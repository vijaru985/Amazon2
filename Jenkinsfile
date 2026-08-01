pipeline {

    agent any

    tools {
        maven 'Default Maven'
        jdk 'Default JDK'
    }

    stages {

        stage('Run Tests') {

            steps {

                bat 'mvn clean test'

            }

        }

    }

    post {

        always {

            script {

                if (!fileExists('target/cucumber.json')) {

                    emailext(
                        to: 'vijayruttala985@gmail.com',
                        subject: 'Automation Report',
                        body: 'target/cucumber.json not found.'
                    )

                    return
                }

                def jsonText = readFile('target/cucumber.json')

                def json = new groovy.json.JsonSlurperClassic().parseText(jsonText)

                int total = 0
                int passed = 0
                int failed = 0

                String rows = ""

                json.each { feature ->

                    feature.elements.each { scenario ->

                        total++

                        String scenarioName = scenario.name

                        String status = "Passed"

                        String methods = ""

                        scenario.steps.each { step ->

                            if(step.match?.location && step.result?.status!="skipped"){

                                String method=step.match.location.tokenize(".").last()

                                method=method.replaceAll("\\(.*\\)","()")

                                methods += "<span style='color:#1565C0;font-weight:bold;'>✔ ${method}</span><br/>"

                            }

                            if(step.result?.status=="failed"){

                                status="Failed"

                            }

                        }

                        if(status=="Passed")
                            passed++
                        else
                            failed++

                        rows += """

<tr>

<td>${scenarioName}</td>

<td>${methods}</td>

<td align="center">

<span style="
background:${status=="Passed" ? "#D4EDDA" : "#F8D7DA"};
color:${status=="Passed" ? "#155724" : "#721C24"};
padding:6px 14px;
border-radius:20px;
font-weight:bold;
">

${status=="Passed" ? "✔ PASSED" : "✘ FAILED"}

</span>

</td>

</tr>

"""

                    }

                }

                String body="""

<html>

<head>

<style>

body{

font-family:Arial,Helvetica,sans-serif;

background:#F4F7FB;

padding:20px;

color:#333;

}

.header{

background:#0B2C6B;

color:white;

padding:20px;

text-align:center;

border-radius:10px;

font-size:32px;

font-weight:bold;

}

.subtitle{

font-size:16px;

color:#D6E4FF;

margin-top:8px;

}

.summary{
width:100%;
border-collapse:separate;
border-spacing:10px;
margin:15px 0;
}

.card{
border-radius:8px;
padding:10px;
text-align:center;
color:white;
font-size:15px;
font-weight:bold;
height:85px;
line-height:20px;
box-shadow:0 2px 5px rgba(0,0,0,0.15);
}

.total{

background:#1565C0;

}

.pass{

background:#2E7D32;

}

.fail{

background:#C62828;

}

table{

width:100%;

border-collapse:collapse;

background:white;

box-shadow:0 2px 8px rgba(0,0,0,0.15);

}

th{

background:#0B2C6B;

color:white;

padding:12px;

}

td{

padding:10px;

border:1px solid #DDDDDD;

vertical-align:top;

}

tr:nth-child(even){

background:#F7F9FC;

}

.footer{

margin-top:25px;

padding:15px;

background:#EEEEEE;

border-radius:10px;

line-height:28px;

}

</style>

</head>

<body>

<div class="header">

Automation Test Execution Report

<div class="subtitle">

Selenium • Cucumber • Java • Jenkins

</div>

</div>

<table class="summary">

<tr>

<td>

<div class="card total">
📋<br>
<div style="font-size:14px;margin-top:6px;">Total Scenarios</div>
<div style="font-size:28px;font-weight:bold;margin-top:6px;">${total}</div>
</div>

</td>

<td>

<div class="card pass">

✅<br><br>

Passed

<br><br>

<span style="font-size:30px;">

${passed}

</span>

</div>

</td>

<td>

<div class="card fail">
❌<br>
<div style="font-size:14px;margin-top:6px;">Failed</div>
<div style="font-size:28px;font-weight:bold;margin-top:6px;">${failed}</div>
</div>

</td>

</tr>

</table>

<table>

<tr>

<th width="45%">

Scenario

</th>

<th width="40%">

Executed Test Methods

</th>

<th width="15%">

Status

</th>

</tr>

${rows}

</table>

<div class="footer">

<b>Project :</b> Amazon Automation Framework<br>

<b>Job :</b> ${env.JOB_NAME}<br>

<b>Build :</b> #${env.BUILD_NUMBER}<br>

<b>Environment :</b> QA<br>

<b>Browser :</b> Chrome<br>

<b>Execution Time :</b> ${new Date()}<br>

</div>

</body>

</html>

"""

                emailext(

                    to: 'vijayruttala985@gmail.com',

                    subject: "Automation Test Execution Report | ${env.JOB_NAME} #${env.BUILD_NUMBER}",

                    mimeType: 'text/html',

                    body: body,

                    attachmentsPattern: 'target/cucumber-report.html'

                )

            }

        }

    }

}