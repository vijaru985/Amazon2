package utility;

import java.time.Duration;
import java.util.Optional;

import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v148.network.Network;
import org.openqa.selenium.devtools.v148.network.model.RequestId;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chromium.ChromiumDriver;

public class DevToolsManager {

    private final DevTools devTools;

    // API information
    private volatile boolean eligibilityApiCalled = false;

    private volatile String eligibilityApiUrl = "";

    private volatile int statusCode = 0;

    private volatile String responseBody = "";

    private volatile boolean eligibilityResponseReceived = false;

    private RequestId requestId;

    public DevToolsManager(ChromiumDriver driver) {

        devTools = driver.getDevTools();

        devTools.createSession();

        devTools.send(
                Network.enable(
                        Optional.empty(),
                        Optional.empty(),
                        Optional.empty(),
                        Optional.empty(),
                        Optional.empty()
                )
        );
    }

    /**
     * Listens for outgoing requests and identifies
     * the eligibility API.
     */
    public void captureEligibilityRequest() {

        devTools.addListener(
                Network.requestWillBeSent(),
                request -> {

                    String url =
                            request.getRequest().getUrl();

                    if (url.contains("eligibility")) {

                        eligibilityApiCalled = true;

                        eligibilityApiUrl = url;

                        Log.logger.info(
                                "Eligibility API Called: {}",
                                url
                        );
                    }
                }
        );
    }

    /**
     * Listens for the eligibility API response.
     */
    public void captureEligibilityResponse() {

        devTools.addListener(
                Network.responseReceived(),
                response -> {

                    String url =
                            response.getResponse().getUrl();

                    if (url.contains("eligibility")) {

                        statusCode =
                                response.getResponse()
                                        .getStatus()
                                        .intValue();

                        requestId =
                                response.getRequestId();

                        try {

                            Network.GetResponseBodyResponse body =
                                    devTools.send(
                                            Network.getResponseBody(
                                                    requestId
                                            )
                                    );

                            responseBody =
                                    body.getBody();

                            eligibilityResponseReceived = true;

                            Log.logger.info(
                                    "Eligibility API Response received. Status Code: {}",
                                    statusCode
                            );

                        } catch (Exception e) {

                            Log.logger.error(
                                    "Failed to retrieve eligibility API response body",
                                    e
                            );
                        }
                    }
                }
        );
    }

    /**
     * Waits until eligibility API request is captured.
     */
    public boolean waitForEligibilityApiCall(
            Duration timeout) {

        long endTime =
                System.currentTimeMillis()
                        + timeout.toMillis();

        while (System.currentTimeMillis() < endTime) {

            if (eligibilityApiCalled) {
                return true;
            }

            try {

                Thread.sleep(100);

            } catch (InterruptedException e) {

                Thread.currentThread().interrupt();

                return false;
            }
        }

        return false;
    }

    /**
     * Waits until eligibility API response is received.
     */
    public boolean waitForEligibilityApiResponse(
            Duration timeout) {

        long endTime =
                System.currentTimeMillis()
                        + timeout.toMillis();

        while (System.currentTimeMillis() < endTime) {

            if (eligibilityResponseReceived) {
                return true;
            }

            try {

                Thread.sleep(100);

            } catch (InterruptedException e) {

                Thread.currentThread().interrupt();

                return false;
            }
        }

        return false;
    }

    public boolean isEligibilityApiCalled() {

        return eligibilityApiCalled;
    }

    public String getEligibilityApiUrl() {

        return eligibilityApiUrl;
    }

    public int getStatusCode() {

        return statusCode;
    }

    public String getResponseBody() {

        return responseBody;
    }

    public boolean isEligibilityResponseReceived() {

        return eligibilityResponseReceived;
    }
    
    public boolean waitForEligibilityApiResponse(
            WebDriver driver,
            Duration timeout) {

        try {

            WebDriverWait wait =
                    new WebDriverWait(driver, timeout);

            return wait.until(
                    d -> eligibilityResponseReceived
            );

        } catch (TimeoutException e) {

            return false;
        }
    }
}