package com.tboi.game.tween;

import com.badlogic.gdx.graphics.g2d.Sprite;

import aurelienribon.tweenengine.TweenAccessor;

public class SpriteTween implements TweenAccessor<Sprite> {

    public static final int
            FADING = 0,
            FLOAT = 1;

    @Override
    public int getValues(Sprite target, int tweenType, float[] returnValues) {
        switch (tweenType) {
        case FADING:
            returnValues[0] = target.getColor().a;
            return 1;
        case FLOAT:
            returnValues[0] = target.getY();
            return 1;
        default:
            assert false;
            return -1;
        }
    }

    @Override
    public void setValues(Sprite target, int tweenType, float[] newValues) {
        switch (tweenType) {
            case FADING:
                target.setColor(target.getColor().r,target.getColor().g,target.getColor().b,newValues[0]);
                break;
            case FLOAT:
                target.setY(newValues[0]);
                break;
            default:
                assert false;
                break;
        }
    }
}
