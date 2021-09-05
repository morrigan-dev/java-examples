<p align="center">
 <img src="https://github.com/morrigan-dev/java-examples/blob/main/images/Java-Examples.png">
</p>

<p align="center">
    <a href="https://github.com/morrigan-dev/java-examples/actions/workflows/build-job.yml" title="Build Job"><img src="https://img.shields.io/github/workflow/status/morrigan-dev/java-examples/Run%20snapshot%20build-job?logo=GitHub&style=plastic"></a>
    <a href="https://github.com/morrigan-dev/java-examples/actions/workflows/quality-job.yml" title="Quality Job"><img src="https://img.shields.io/github/workflow/status/morrigan-dev/java-examples/Run%20quality%20build-job?label=quality-build&logo=GitHub&style=plastic"></a>
    <a href="https://github.com/morrigan-dev/java-examples/blob/main/LICENSE" title="License"><img src="https://img.shields.io/github/license/morrigan-dev/java-examples?logo=GitHub&style=plastic"></a>
    <a href="https://github.com/morrigan-dev/java-examples" title="Last Commit"><img src="https://img.shields.io/github/last-commit/morrigan-dev/java-examples?logo=GitHub&style=plastic"></a>
	<br>    
    <a href="https://sonarcloud.io/dashboard?id=morrigan-dev_java-examples" title="Quality Gate"><img src="https://img.shields.io/sonar/quality_gate/morrigan-dev_java-examples?logo=SonarCloud&server=https%3A%2F%2Fsonarcloud.io&style=plastic"></a>
    <a href="https://sonarcloud.io/dashboard?id=morrigan-dev_java-examples" title="Successful tests"><img src="https://img.shields.io/sonar/test_success_density/morrigan-dev_java-examples?logo=SonarCloud&server=https%3A%2F%2Fsonarcloud.io&style=plastic"></a>
    <a href="https://sonarcloud.io/dashboard?id=morrigan-dev_java-examples" title="Coverage"><img src="https://img.shields.io/sonar/coverage/morrigan-dev_java-examples?logo=SonarCloud&server=https%3A%2F%2Fsonarcloud.io&style=plastic"></a>
</p>

<hr />
<p align="center">
    <a href="#über-dieses-projekt">Über dieses Projekt</a> • 
    <a href="#github">GitHub</a> • 
    <a href="#sonarcloud">Sonarcloud</a> • 
    <a href="#jfrog-artifactory">JFrog Artifactory</a> • 
    <a href="#fazit">Fazit</a>
</p>
<hr />

## Über dieses Projekt

In diesem Projekt wird demonstriert, wie mit Hilfe reiner Cloud Dienste ein kostenloser CI Entwicklungsprozess für open source Projekte und kleinen Entwicklerteams erstellt werden kann.
Das Projekt dient als Vorlage für weitere Projekte und beinhaltet in den verschiedenen Modulen viele weitere hilfreiche Beispiele aus den verschiedensten Bereichen. So kann das Grundgerüst als Vorlage für weitere Projekte genutzt werden und auf die Beispiele je nach Bedarf zurückgegriffen werden.
Das Projekt basiert auf folgenden Technologien:
* [GitHub](https://github.com/morrigan-dev) (als SCM)
* [GitHub actions](https://docs.github.com/en/free-pro-team@latest/actions) (als CI/CD Pipeline)
* [Sonarcloud.io](https://sonarcloud.io/organizations/morrigan-dev/projects) (als Quality Werkzeug)
* [JFrog Artifactory](https://morrigan.jfrog.io/ui/packages) (als Artefakt Repository)

Folgende grundlegenden Frameworks/Plugins kommen zum Einsatz:
* [Apache Maven](http://maven.apache.org/) (als Build & Dependency Werkzeug)
* [Slf4j](http://www.slf4j.org/) und [log4j2](https://logging.apache.org/log4j/2.x/) (als Logging Framework)
* [JUnit4](https://junit.org/junit4/) als Unit-Test Framework
* [JaCoCo](https://www.eclemma.org/jacoco/) als Coverage Tool

## GitHub

### Versionskontrolle

Die Basis für einen CI Entwicklungsprozess sind die Sourcen, die stetig weiterentwickelt und getestet werden. Daher beginnt ein CI Entwicklungsprozess immer mit der Versionsverwaltung, in der die Sourcen versioniert abgelegt werden. Grundsätzlich spielt es keine Rolle, welche Versionsverwaltung genutzt wird.

Für die hier veröffentlichten Projekte wurde GitHub ausgewählt. Alternativ kann aber genauso auch GitLab genutzt werden. Der Funktionsumfang wächst bei beiden Plattformen stetig weiter. Für mich als privaten Entwickler reicht daher der gebotene Funktionsumfang völlig aus. Wichtige Gründe für mich sind:
* Stabilität
* Große Community
* Viele Beispiele
* Viele Anleitungen
* Einfache selbstständige Einarbeitung
* Integration/Zusammenspiel von anderen Werkzeugen

**Quellen**
* [GitLab vs Github — What Are The Key Differences And Which One Is Better? (2020 Update)](https://blog.codegiant.io/gitlab-vs-github-which-one-is-better-2020-d8ec7fb9542c)
* [GitLab vs GitHub Comparison](https://about.gitlab.com/devops-tools/github-vs-gitlab/)

### CI/CD Pipeline

Auf Basis einer Versionskontrolle kann nun ein CI Entwicklungsprozess aufgesetzt werden. Sobald also Änderungen an den Sourcen getätigt werden, sollen diese compiliert und getestet werden. Auch hierfür bietet sich GitHub an, da es mit den GitHub actions einen integrierten CI/CD Mechanismus mitbringt.

Bei diesem Beispielprojekt wird auf drei verschiedene Jobs gesetzt.
* Ein Build-Job
* Ein Quality-Job
* Ein Release-Job

Dabei hat jeder Job eine ganz spezifische Aufgabe.

#### Build-Job

Der Build-Job ist verantwortlich, dass bei Änderungen an den Sourcen unabhängig vom Branch, die geänderten Sourcen ausgecheckt und compiliert werden. Dabei sollen auch Unit-Tests durchgeführt werden und bei Fehlern und fehlgeschlagenen Tests, soll der Verursacher über den Fehlschlag direkt informiert werden.
Bei einem erfolgreichen Durchlauf werden die erzeugten Artefakte als SNAPSHOT Version in einem Artefakt Repository abgelegt.

#### Quality-Job

Der Quality-Job ist verantwortlich für die Qualität der Sourcen. Da die Analyse etwas aufwändiger sein kann, wird dieser Job nicht bei jeder Änderung, sondern einmal nachts oder durch manuelles Anstoßen gestartet. Hier werden ebenso, wie beim Build-Job die Sourcen compiliert und Tests ausgeführt. Zusätzlich wird aber auch eine statische Codeanalyse durchgeführt und ein Bericht erstellt mit dem es dem Entwicklerteam möglich ist im Nachgang noch kritische Stellen zu korrigieren.

#### Release-Job

Der Release-Job ist verantwortlich für die Erstellung fertiger Releases. Dabei werden Release Versionen erstellt, in der Versionskontrolle getagt und finale Release Versionen im Artifakt Repository bereitgestellt.

## Sonarcloud

Sonarcloud ist ein Werkzeug für die statische Quellcode Analyse. Es werden die gängisten Sprachen unterstützt und das Werkzeug kann sowohl in den GitHub CI Prozess, als auch in die IDE integriert werden. So werden die Analysen automatisiert durch den Quality-Job angestoßen und können durch die IDE Integration direkt während der Entwicklung beachtet werden.

## JFrog Artifactory

Sowohl im Build- als auch im Release-Job wurde bereits angesprochen, dass Sourcen compiliert werden und die resultierenden Artefakte in ein Repository abgelegt werden. Diese Aufgabe übernimmt das JFrog Artifactory. Die dort zur Verfügung gestellten Artefakte können dann von einem maven Buildprozess diese Artefakte als Dependencies wiederverwenden.

Somit schließt sich der Kreis des Continuous Integration Prozesses, bei dem Anwendungen und Bibliotheken entwickelt, getestet, analysiert, deployt und wiederverwendet werden.

## Fazit

Dieses Projekt dient als Vorlage eines möglichen CI Prozesses mit den aufgeführten Werkzeugen. Es gibt mit Sicherheit viele andere Möglichkeiten. Ich habe mich für meine Projekte für diesen entschieden und nutze diese Vorlage auch für mich selbst für alle künftigen Projekte.

Es ist häufig schwierig aus großen Projekten die Aspekte zu identifizieren, die für bestimmte Zwecke benötigt werden. Daher ist das Vorgehen einmal zentral hier in diesem Beispiel-Projekt dokumentiert.