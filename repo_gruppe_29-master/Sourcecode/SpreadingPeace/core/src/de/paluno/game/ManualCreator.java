package de.paluno.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Properties;

import com.badlogic.gdx.Gdx;
import de.paluno.game.SEPgame;
import de.paluno.game.gameobjects.Colour;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;




public class ManualCreator extends com.badlogic.gdx.ScreenAdapter implements Screen {


	public static void createFile() {
			try {
				if(!checkFile()) {
				System.out.println("bedienungsanleitung wird erstellt");
	            String userHomeFolder = System.getProperty("user.home");
	            File manual = new File(userHomeFolder, "manual.txt");
//	            BufferedWriter out = new BufferedWriter(new FileWriter(textFile));
	            FileWriter f=new FileWriter(manual);
	            f.write("Willkommen bei SPREADING PEACE. Der Fakt, dass Sie das hier lesen, bedeutet entweder, dass Sie "+System.getProperty("line.separator"));
	            f.write("nicht wissen, wie das Spiel  funktioniert oder dass Sie diese Datei auf ihrer Festplatte entdeckt haben "+System.getProperty("line.separator"));
	            f.write("und sich fragen, ob Sie gelöscht werden kann. Zum zweiten: Ja, aber sobald Sie die spieltechnische "+System.getProperty("line.separator"));
	            f.write("Glanzleistung namens SPREADING PEACE erneut starten, wird diese Datei neu erstellt. "+System.getProperty("line.separator"));
	            f.write(System.getProperty("line.separator"));
	            f.write("Zur Gamepadsteuerung:"+System.getProperty("line.separator"));
	            f.write(System.getProperty("line.separator"));
	            f.write("Gehen Sie im Hauptmenü in die Preferences. In die Felder tragen Sie ein:"+System.getProperty("line.separator"));
	            f.write(System.getProperty("line.separator"));
	            f.write("Fahren – hier tippen Sie ein:  dpad, für Fahrsteuerung über das Steuerkreuz. LS, für den linken Stick "+System.getProperty("line.separator"));
	            f.write("oder RS für den rechten Stick."+System.getProperty("line.separator"));
	            f.write(System.getProperty("line.separator"));
	            f.write("Links drehen: Wählen Sie die Taste aus, die die Kanone des Panzers nach links drehen soll. "+System.getProperty("line.separator"));
	            f.write("Standardmäßig ist das die linke Schultertaste. "+System.getProperty("line.separator"));
	            f.write(System.getProperty("line.separator"));
	            f.write("Rechts drehen: Wählen Sie die Taste aus, die die Kanone des Panzers nach rechts drehen soll. "+System.getProperty("line.separator"));
	            f.write("Standardmäßig ist das die rechte Schultertaste. "+System.getProperty("line.separator"));
	            f.write(System.getProperty("line.separator"));
	            f.write("Flower hoch-/runterschalten - Wählen Sie die Taste aus, die den aktuell ausgewählten Blumentyp "+System.getProperty("line.separator"));
	            f.write("ändert. Standardmäßig sind das die rechte-/bzw. linke taste neben dem Xbox Logo."+System.getProperty("line.separator"));
	            f.write(System.getProperty("line.separator"));
	            f.write("Schiessen - Wählen Sie die Taste aus, die den aktuell ausgewählten Blumentyp abfeuert. "+System.getProperty("line.separator"));
	            f.write("Standardmäßig sind das die A Taste."+System.getProperty("line.separator"));
	            f.write(System.getProperty("line.separator"));
	            f.write("Pause - Wählen Sie die Taste aus, die das Spiel pausieren soll. Standardmäßig ist das die Y Taste."+System.getProperty("line.separator"));
	            f.write("Mögliche Einträge zur eigenen Belegung der Funktionen sind: RT für die rechte Schultertaste, "+System.getProperty("line.separator"));
	            f.write("LT für die linke Schultertaste, die Tasten A, B, X, Y, RX für die Taste rechts vom Xbox Knopf, "+System.getProperty("line.separator"));
	            f.write("LX für die Taste links vom Xbox Knopf, rsdown für das Runterdrücken des rechten Sticks, "+System.getProperty("line.separator"));
	            f.write("lsdown für das Runterdrücken des linken Sticks. "+System.getProperty("line.separator"));
	            f.write(System.getProperty("line.separator"));
	            f.write(System.getProperty("line.separator"));
	            f.write(System.getProperty("line.separator"));
	            f.write("Zur Tastatursteuerung: "+System.getProperty("line.separator"));
	            f.write("Gehen Sie im Hauptmenü in die Preferences. In die Felder tragen Sie ein:"+System.getProperty("line.separator"));
	            f.write(System.getProperty("line.separator"));
	            f.write("Oben, Unten, Links, Rechts – hier tippen Sie die Tasten für die Fahrsteuerung ihres Panzers ein. "+System.getProperty("line.separator"));
	            f.write("Standardmäßig ist das das WASD layout."+System.getProperty("line.separator"));
	            f.write(System.getProperty("line.separator"));
	            f.write("Links drehen: Wählen Sie die Taste aus, die die Kanone des Panzers nach links drehen soll."+System.getProperty("line.separator"));
	            f.write("Standardmäßig ist das der Buchstabe O. "+System.getProperty("line.separator"));
	            f.write(System.getProperty("line.separator"));
	            f.write("Rechts drehen: Wählen Sie die Taste aus, die die Kanone des Panzers nach rechts drehen soll. "+System.getProperty("line.separator"));
	            f.write("Standardmäßig ist das der Buchstabe P. "+System.getProperty("line.separator"));
	            f.write(System.getProperty("line.separator"));
	            f.write("Schiessen - Wählen Sie die Taste aus, die den aktuell ausgewählten Blumentyp abfeuert. "+System.getProperty("line.separator"));
	            f.write("Standardmäßig sind das die Leertaste."+System.getProperty("line.separator"));
	            f.write(System.getProperty("line.separator"));
	            f.write("xxx-Flower: Wählen Sie die jeweiligen Tasten für den jeweiligen Blumentypen aus."+System.getProperty("line.separator"));
	            f.write(System.getProperty("line.separator"));
	            f.write("Pause - Wählen Sie die Taste aus, die das Spiel pausieren soll. Standardmäßig ist das die Q Taste."+System.getProperty("line.separator"));
	            f.write(System.getProperty("line.separator"));
	            f.write(System.getProperty("line.separator"));
	            f.write("Die Tastatur & Maussteuerung funktioniert nach dem gleichen Prinzip, nur wird hier die Drehung der "+System.getProperty("line.separator"));
	            f.write("Kanone per Mauszeiger gesteuert und das Standardlayout ist anders."+System.getProperty("line.separator"));
	            f.write(System.getProperty("line.separator"));
	            f.write("Bitte achten Sie darauf, keine Funktion unbelegt oder einzelne Tasten doppelt zu belegen."+System.getProperty("line.separator"));
	            f.write(System.getProperty("line.separator"));
	            f.write("Nun müssen Sie unbedingt die gewünschte Steuerung im Hauptmenü unter ‚Teams‘ noch einstellen. "+System.getProperty("line.separator"));
	            f.write("Machen Sie für den jeweiligen Spieler einen Haken bei dessen gewünschter Steuerung. Die KI-"+System.getProperty("line.separator"));
	            f.write("Checkbox lässt den Panzer durch eine KI gesteuert werden (nicht in allen Spielmodi). "+System.getProperty("line.separator"));
	            f.write("Sie können hier auch die Teamaufstellung der Panzer ändern (nicht in allen Spielmodi)."+System.getProperty("line.separator"));
	            f.write(System.getProperty("line.separator"));
	            f.write(System.getProperty("line.separator"));
	            f.write("Viel Spaß mit SPREADING PEACE.");
	            
	            
	            f.close();
				}
				else
				{
					System.out.println("bedienungsanleitung existiert bereits");
				}

	        } catch (IOException e) {
	            e.printStackTrace();
	        }

	}


	private static boolean checkFile() {
		File f = new File(System.getProperty("user.home")+"/manual.txt");
		if(f.exists()&&!(f.isDirectory())) { 
		    return true;
		}
		else return false;
	}

}
