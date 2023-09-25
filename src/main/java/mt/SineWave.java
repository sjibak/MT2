package mt;
import mt.Signal;

public class SineWave extends Signal
{
	public SineWave(int numWaves, int numSamples) {
	    super(numSamples, "Sine " + numWaves);	
        for (int i = 0; i < buffer.length; ++i) {
            buffer[i] = (float) Math.sin(i * Math.PI * 2.0 / numSamples * numWaves);
        }
	}
}

