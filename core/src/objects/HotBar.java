package objects;

import com.badlogic.gdx.graphics.Texture;

public class HotBar {
    public float speedTextX;
    public float speedTextY;
    public float manaTextX;
    public float manaTextY;
    public float regenTextX;
    public float regenTextY;
    public float speedTextWidth;
    public float speedTextHeight;
    public float manaTextWidth;
    public float manaTextHeight;
    public float regenTextWidth;
    public float regenTextHeight;
    public float warpTextureX;
    public float warpTextureY;
    public float paralysisTextureX;
    public float paralysisTextureY;

    public HotBar(float sTx, float sTY, float mTX, float mTY, float rTX, float rTY, float speedTextWidth,float speedTextHeight,float manaTextWidth,float manaTextHeight,float regenTextWidth,float regenTextHeight, float warpTextureX, float warpTextureY, float paralysisTextureX, float paralysisTextureY){
        this.speedTextX = sTx;
        this.speedTextY = sTY;
        this.manaTextX = mTX;
        this.manaTextY = mTY;
        this.regenTextX = rTX;
        this.regenTextY = rTY;
        this.speedTextWidth = speedTextWidth;
        this.speedTextHeight = speedTextHeight;
        this.manaTextWidth = manaTextWidth;
        this.manaTextHeight = manaTextHeight;
        this.regenTextWidth = regenTextWidth;
        this.regenTextHeight = regenTextHeight;
        this.warpTextureX = warpTextureX;
        this.warpTextureY = warpTextureY;
        this.paralysisTextureX = paralysisTextureX;
        this.paralysisTextureY = paralysisTextureY;
    }
}
