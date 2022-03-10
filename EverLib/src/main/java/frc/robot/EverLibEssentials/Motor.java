package frc.robot.EverLibEssentials;

import edu.wpi.first.wpilibj.motorcontrol.MotorController;

public class Motor implements MotorController{
    private boolean m_inverted = false;
    private final int m_INDEX;

    public Motor(MotorController motor){
        this.m_INDEX = Motors.AddMotor(motor);
    }

    @Override
    public void set(double speed) {
        Motors.setSpeed(this.m_INDEX, speed * (m_inverted ? 1 : -1));
    }

    @Override
    public double get() {
        return Motors.getSpeedAt(this.m_INDEX);
    }

    @Override
    public void setInverted(boolean isInverted) {
        this.m_inverted = isInverted;
    }

    @Override
    public boolean getInverted() {
        return this.m_inverted;
    }

    @Override
    public void disable() {
        this.set(0);
        
    }

    @Override
    public void stopMotor() {
        this.set(0);
    }

}
