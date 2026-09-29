Snake (2 Spieler)

Snake für zwei Spieler auf einem Bildschirm, geschrieben in Java mit Swing. Beide Schlangen spielen gleichzeitig auf demselben Feld und kämpfen um das Futter.

Funktionen
2-Spieler-Modus: Spieler 1 (blau) und Spieler 2 (grün) auf einem Bildschirm
Kollisionsabfrage mit Wand, der eigenen Schlange und der gegnerischen Schlange
Sieger-Anzeige am Ende: Spieler 1 gewinnt, Spieler 2 gewinnt oder Unentschieden, wenn beide gleichzeitig verlieren
Punktezähler für beide Spieler
Das Spiel wird mit jedem gefressenen Futter schneller
Futter erscheint nur an freien Feldern
Neustart-Button nach dem Spielende
Steuerung
Spieler	Tasten
Spieler 1 (blau)	W, A, S, D
Spieler 2 (grün)	Pfeiltasten

Eine direkte 180°-Drehung ist nicht möglich, damit sich eine Schlange nicht sofort selbst trifft.

Starten

Voraussetzung: ein installiertes JDK (Java 8 oder neuer).

javac *.java
java SnakeGui
Aufbau

Das Projekt trennt Daten, Spiellogik und Oberfläche:

Datei	Aufgabe
SnakeSegment.java	Ein Block der Schlange (Position x, y)
Snake.java	Daten einer Schlange: Körper, Kopf, Punkte, Bewegung, Selbst- und Fremdkollision
SnakeSteuerung.java	Spiellogik: Spielschritt, Futter, Geschwindigkeit, Gewinner, Neustart
SnakeGui.java	Fenster, Zeichnen des Spielfelds, Tastatureingaben, Spieltakt per Timer
Technik
Java
Swing / AWT für die Oberfläche
javax.swing.Timer als Spieltakt
