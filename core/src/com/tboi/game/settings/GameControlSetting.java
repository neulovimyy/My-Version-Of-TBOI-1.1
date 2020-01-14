package com.tboi.game.settings;


import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.tboi.game.entities.MainCharacter;

public class GameControlSetting {
    public boolean isAndroid;
    public static final Application.ApplicationType ANDROID = Application.ApplicationType.Android;
    public final Application.ApplicationType IOS = Application.ApplicationType.iOS;

    /**
     * this class is used to configure control for multiple platform
     *
     * Author : neulovimyy
     */

    GameControlSetting setting;

    int offset = 20; //main offset

    public int count;

    public void setCount(int count) {
        this.count = count;
    }
    public void platformControlConfig(MainCharacter mc, float dt, Touchpad move, Touchpad attack) {

        setting = new GameControlSetting();

        if(Gdx.app.getType() == ANDROID || Gdx.app.getType() == IOS) { //if mobile
            //main control
            if(move.isTouched()) {
                move.setColor(move.getColor().r, move.getColor().g, move.getColor().b, 0);
                if (move.getKnobX() > move.getKnobX() / 2 + offset) { //d
                    mc.body.applyLinearImpulse(new Vector2(5/100f,0f),mc.body.getWorldCenter(),true);
                    setting.setCount(3);
                }
                if (move.getKnobX() < move.getKnobX() / 2 - offset) { //a
                    mc.body.applyLinearImpulse(new Vector2(-5/100f,0f),mc.body.getWorldCenter(),true);
                    setting.setCount(2);
                }
                if (move.getKnobY() > move.getKnobY() / 2 + offset) { //w
                    mc.body.applyLinearImpulse(new Vector2(0f,5/100f),mc.body.getWorldCenter(),true);
                    setting.setCount(1);
                }
                if (move.getKnobY() < move.getKnobY() / 2 - offset) { //s
                    mc.body.applyLinearImpulse(new Vector2(0f,-5/100f),mc.body.getWorldCenter(),true);
                    setting.setCount(0);
                }
            } else {
                move.setColor(move.getColor().r, move.getColor().g, move.getColor().b, 1);
            }

        } else {
            //if platform = desktop, remove all on-screen controller then set controls to keyboard

            move.setColor(move.getColor().r, move.getColor().g, move.getColor().b, 0);
            attack.setColor(move.getColor().r, move.getColor().g, move.getColor().b, 0);

            if(Gdx.input.isKeyPressed(Input.Keys.D)){
                mc.body.applyLinearImpulse(new Vector2( 0.5f/100,0f),mc.body.getWorldCenter(),true);
                setting.setCount(3);
            }
            if(Gdx.input.isKeyPressed(Input.Keys.A)){
                mc.body.applyLinearImpulse(new Vector2(-0.5f/100,0f),mc.body.getWorldCenter(),true);
                setting.setCount(2);
            }
            if(Gdx.input.isKeyPressed(Input.Keys.W)) {
                mc.body.applyLinearImpulse(new Vector2(0,0.5f/100),mc.body.getWorldCenter(),true);
                setting.setCount(1);
            }
            if(Gdx.input.isKeyPressed(Input.Keys.S)){
                mc.body.applyLinearImpulse(new Vector2(0,-0.5f/100),mc.body.getWorldCenter(),true);
                setting.setCount(0);
            }
        }
    }

    public int getCount() {
        return count;
    }
}