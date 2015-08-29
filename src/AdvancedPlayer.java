import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


public class AdvancedPlayer implements LineListener {
	boolean playCompleted;
    
    /**
     * Play a audio file which pass as audioFilePath 
     * @param audioFilePath Absolute path of the audio file.
     */
    void playAudioFile(String audioFilePath) {
        File audioFile = new File(audioFilePath);
 
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);
 
            AudioFormat audioFormat = audioInputStream.getFormat();
 
            DataLine.Info formatInfo = new DataLine.Info(Clip.class, audioFormat);
 
            Clip clip = (Clip) AudioSystem.getLine(formatInfo);
 
            clip.addLineListener(this);
 
            clip.open(audioInputStream);
             
            clip.start();
             
            while (!playCompleted) {
                // wait for the playback completes
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
             
            clip.close();
             
        } catch (UnsupportedAudioFileException e) {
            System.out.println("Error on not supporting file format");
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            System.out.println("Error occured due to unavailable of Audio line for playing.");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Error occured in audio play.");
            e.printStackTrace();
        }
         
    }

	@Override
	public void update(LineEvent event) {
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String[] args) {
		AdvancedPlayer player=new AdvancedPlayer();
		String audioFilePath="resource/Alarm01.wav";
		String absolutePath=AdvancedPlayer.class.getResource(audioFilePath).getFile().toString();
		player.playAudioFile(absolutePath);
	}
}
