<p align="center">
 <img src="https://raw.githubusercontent.com/morrigan-dev/java-examples/main/multi-module-ci-project/images/Multi-Modul CI Projekt Grundgesrüst.png">
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
Das Projekt dient als Vorlage für weitere Projekte und beinhaltet daher nur das techische Grundgerüst. Es gibt also keinen nennenswerten fachlichen Inhalt, den dieses Projekt bereitstellt.
Das Projekt basiert auf folgenden Technologien:
* GitHub (SCM)
* GitHub actions (CI/CD Pipeline)
* Sonarcloud (Quality Check)
* JFrog Artifactory (Artefact Repository)

Folgende Frameworks/Plugins kommen zum Einsatz:
* Apache Maven
* Slf4j und log4j
* JUnit

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
 # Ein Build-Job
 # Ein Quality-Job
 # Ein Release-Job
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