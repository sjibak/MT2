/*
 * SineWave2d.java
 * Copyright (C) 2021 Stephan Seitz <stephan.seitz@fau.de>
 *
 * Distributed under terms of the GPLv3 license.
 */
package mt;

public class SineWave2d extends Image {

    public SineWave2d(Vector2d k, int width, int height, String name) {
        super(width, height, name);
        this.minIndexX = -width / 2;
        this.minIndexY = -height / 2;

        for (int y = minIndexY; y < minIndexY + height(); ++y) {
            for (int x = minIndexX; x < minIndexX + width(); ++x) {
                float value = (float) Math.sin(new Vector2d(x / (float) width, y / (float) height).dot(k) * 2.f * Math.PI);
                setAtIndex(x, y, value);
            }
        }

    }
}
