package sound;

import javax.sound.sampled.*;
import java.io.File;

public class SoundPlayer {
    private Clip clip;

    public void loadSound(String filePath) {
        try {
            // 加载音频文件
            File soundFile = new File(filePath);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reloadSound(String filePath) {
        if (clip != null) {
            clip.close();
        }
        loadSound(filePath);
    }

    public void play() {
        if (clip != null) {
            if (clip.isRunning()) {
                clip.stop();
            }
            clip.setFramePosition(0);
            clip.start();
        }
    }

    public void stop() {
        if (clip != null) {
            clip.stop();
        }
    }
}
