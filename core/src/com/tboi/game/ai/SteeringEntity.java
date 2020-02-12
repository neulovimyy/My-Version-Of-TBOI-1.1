package com.tboi.game.ai;

import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.ai.steer.SteeringBehavior;
import com.badlogic.gdx.ai.utils.Location;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class SteeringEntity implements Steerable<Vector2>{

    Body body;
    float radius, linSpeed, linAcce, angleSpeed, orientation, angleAcce;
    boolean tagged = false;

    SteeringBehavior<Vector2> sBeve;
    SteeringAcceleration<Vector2> sAcce;

    public SteeringEntity(Body body, float radius){
        this.body = body;
        this.radius = radius;

        this.linAcce = 200; this.linSpeed = 100;
        this.angleAcce = 5; this.angleSpeed = 20;
        this.sAcce = new SteeringAcceleration<Vector2>(new Vector2());
        this.body.setUserData(this);
        this.tagged = false;
    }

    public void update(float delta) {
        if(sBeve != null) {
            sBeve.calculateSteering(sAcce);
            applySteering(delta);
        }
    }

    private void applySteering(float delta) {
        boolean acce = false;
        if(!sAcce.linear.isZero()){
            Vector2 force = sAcce.linear.scl(delta);
            body.applyForceToCenter(force, true);
            acce = true;
        }

        if(acce) {
            Vector2 v = body.getLinearVelocity();
            float css = v.len2(); //current speed squared
            if(css > linSpeed * linSpeed) {
                body.setLinearVelocity(v.scl(linSpeed/(float)Math.sqrt(css)));
            }
        }
    }

    @Override
    public Vector2 getLinearVelocity() {
        return body.getLinearVelocity();
    }
    @Override
    public float getAngularVelocity() {
        return body.getAngularVelocity();
    }
    @Override
    public float getBoundingRadius() {
        return radius;
    }
    @Override
    public boolean isTagged() {
        return tagged;
    }
    @Override
    public void setTagged(boolean tagged) {
        this.tagged = tagged;
    }
    @Override
    public float getZeroLinearSpeedThreshold() {
        return 0;
    }
    @Override
    public void setZeroLinearSpeedThreshold(float value) {
    }
    @Override
    public float getMaxLinearSpeed() {
        return linSpeed;
    }
    @Override
    public void setMaxLinearSpeed(float maxLinearSpeed) {
        this.linSpeed = maxLinearSpeed;
    }
    @Override
    public float getMaxLinearAcceleration() {
        return linAcce;
    }
    @Override
    public void setMaxLinearAcceleration(float maxLinearAcceleration) {
        this.linAcce = maxLinearAcceleration;
    }
    @Override
    public float getMaxAngularSpeed() {
        return angleSpeed;
    }
    @Override
    public void setMaxAngularSpeed(float maxAngularSpeed) {
        this.angleSpeed = maxAngularSpeed;
    }
    @Override
    public float getMaxAngularAcceleration() {
        return angleAcce;
    }
    @Override
    public void setMaxAngularAcceleration(float maxAngularAcceleration) {
        this.angleAcce = maxAngularAcceleration;
    }
    @Override
    public Vector2 getPosition() {
        return body.getPosition();
    }
    @Override
    public float getOrientation() {
        return body.getAngle();
    }
    @Override
    public void setOrientation(float orientation) {
        this.orientation = orientation;
    }
    @Override
    public float vectorToAngle(Vector2 vector) {
        return 0;
    }
    @Override
    public Vector2 angleToVector(Vector2 outVector, float angle) {
        return null;
    }
    @Override
    public Location<Vector2> newLocation() {
        return null;
    }
    public Body getBody(){
        return body;
    }
    public void setsAcce(SteeringAcceleration<Vector2> sAcce) {
        this.sAcce = sAcce;
    }
    public SteeringAcceleration<Vector2> getsAcce() {
        return sAcce;
    }
    public SteeringBehavior<Vector2> getsBeve() {
        return sBeve;
    }
    public void setsBeve(SteeringBehavior<Vector2> sBeve) {
        this.sBeve = sBeve;
    }
}