package com.tboi.game.settings;


import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.tboi.game.entities.MainCharacter;
import com.tboi.game.entities.projectiles.Bullet;

import java.util.ArrayList;

public class GameControlSetting {
    public boolean isAndroid;
    public static final Application.ApplicationType ANDROID = Application.ApplicationType.Android;
    public final Application.ApplicationType IOS = Application.ApplicationType.iOS;
    public final static int WIDTH = 1280;
    public final static int HEIGHT = 720;
    public final static boolean RESIZABLE = false;
    public final static float PPM1 = 1 / 100f;
    public final static float PPM2 = 100f;

    /**
     * this class is used to configure control for multiple platform
     *
     * Author : neulovimyy
     */
    ArrayList<Bullet> bullets;
    GameControlSetting setting;

    int offset = 40; //main offset

    public int count;

    public void setCount(int count) {
        this.count = count;
    }
    public void platformControlConfig(MainCharacter mc, Touchpad move, Touchpad attack) {
        bullets = new ArrayList<Bullet>();
        setting = new GameControlSetting();

        if(Gdx.app.getType() == ANDROID) { //if mobile
            int offset = 50;
            if (move.isTouched()) {
                if (move.getKnobX() >= move.getKnobX() / 2 + offset) { //d
                    mc.body.applyLinearImpulse(new Vector2( 0.5f/100,0f),mc.body.getWorldCenter(),true);
                }
                else { //reverse d
                    mc.body.applyLinearImpulse(new Vector2(-0.5f/100,0f),mc.body.getWorldCenter(),true);
                }
                if (move.getKnobY() >= move.getKnobY() / 2 + offset) { //w
                    mc.body.applyLinearImpulse(new Vector2(0,0.5f/100),mc.body.getWorldCenter(),true);
                }
                else { //reverse w
                    mc.body.applyLinearImpulse(new Vector2(0,-0.5f/100),mc.body.getWorldCenter(),true);
                }
            }

            if (attack.isTouched()) {
                if (attack.getKnobX() >= attack.getKnobX() / 2 + offset) { //d
                    mc.body.applyLinearImpulse(new Vector2( 0.5f/100,0f),mc.body.getWorldCenter(),true);
                }
                else { //reverse d
                    mc.body.applyLinearImpulse(new Vector2(-0.5f/100,0f),mc.body.getWorldCenter(),true);
                }
                if (attack.getKnobY() >= attack.getKnobY() / 2 + offset) { //w
                    mc.body.applyLinearImpulse(new Vector2(0,0.5f/100),mc.body.getWorldCenter(),true);
                }
                else { //reverse w
                    mc.body.applyLinearImpulse(new Vector2(0,-0.5f/100),mc.body.getWorldCenter(),true);
                }
            }

        } else {
            //if platform = desktop, remove all on-screen controller then set controls to keyboard

            move.setColor(move.getColor().r, move.getColor().g, move.getColor().b, 1);
            attack.setColor(move.getColor().r, move.getColor().g, move.getColor().b, 1);

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