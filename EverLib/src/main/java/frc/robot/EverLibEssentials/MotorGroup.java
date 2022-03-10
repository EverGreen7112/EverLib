package frc.robot.EverLibEssentials;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;

public class MotorGroup extends MotorControllerGroup {

    public MotorGroup(MotorController motorController, MotorController[] motorControllers) {
        super(new Motor(motorController), TurnToMotors(motorControllers));
    }


    private static Motor[] TurnToMotors(MotorController[] motorControllers){
        Motor[] motors = new Motor[motorControllers.length];
        for (int i = 0; i < motors.length; i++) {
            motors[i] = new Motor(motorControllers[i]);
        }
        return motors;
    }
    
}
