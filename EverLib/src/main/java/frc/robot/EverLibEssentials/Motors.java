package frc.robot.EverLibEssentials;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import edu.wpi.first.wpilibj.motorcontrol.MotorController;

public class Motors {
    // a list of all MOTORS
    private static final List<MotorController> MOTORS = new ArrayList<MotorController>();
    // a list of all SPEEDS with the indexes matching the correct motor in the MOTORS list
    private static final List<Double> SPEEDS = new ArrayList<Double>();
    // a list of files to record to
    private static final List<File> FILES = new ArrayList<File>();
    // a file containing the settings for the recording
    private static final File SETTINGS_FILE = new File("/home/lvuser/settings.txt");
    // the number of iterations recorded
    private static int m_iterations = 0;
    // a boolean saying wether or not to record
    private static boolean m_record = false;

    // adds a new motor to the group
    public static int AddMotor(MotorController motor){
        MOTORS.add(motor);
        SPEEDS.add(0.0);
        FILES.add(new File(String.format("/home/lvuser/motor%s.txt", MOTORS.size() - 1)));
        return MOTORS.size() - 1;
    }

    // sets the speed of a motor in a specified index
    public static void setSpeed(int index, double speed) {
        SPEEDS.set(index, speed);
    }
    
    // gets the speed of a motor in a specified index
    public static double getSpeedAt(int index) { 
        return SPEEDS.get(index);
    }

    // records actions of motors
    public static void recordMotor(){
        if (m_record){
            m_iterations++; // registers the new iteration

            // registers the new iteration to the settings file
            try {
                SETTINGS_FILE.createNewFile();
                SETTINGS_FILE.setWritable(true);
                FileWriter settings = new FileWriter(SETTINGS_FILE);
                settings.write(String.format("%s\n%s", m_iterations, SPEEDS.size()));
                settings.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            // writes the correct speed at the correct time
            for (int i = 0; i < SPEEDS.size(); i++) {
                try{
                    File file = FILES.get(i);
                    file.setWritable(true, false);
                    file.setReadable(true, false);
                
                    FileWriter fileWriter = new FileWriter(file);
                    
                    
                    if (file.createNewFile()){
                        for (int j = 0; j < m_iterations; j++) {
                            fileWriter.append("0.0\n");
                        }
                    }
                    fileWriter.append(SPEEDS.get(i).toString());
                    fileWriter.close();
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    // plays a recording of the motors
    public static void playRecordedAction(){
        try{
            Scanner settings = new Scanner(SETTINGS_FILE);
            m_iterations = settings.nextInt();
            int motorsToSet = settings.nextInt();
            settings.close();
            double[][] speeds = new double[motorsToSet][m_iterations];

            for (int i = 0; i < m_iterations; i++) {
                for (int j = 0; j < motorsToSet; j++) {
                    Scanner scanner = new Scanner(FILES.get(j));
                    speeds[j][i] = scanner.nextDouble();
                    scanner.close();
                }
            }

            for (double[] ds : speeds) {
                for (int i = 0; i < ds.length; i++) {
                    SPEEDS.set(i, ds[i]);
                }
                runMotors();
            }

            for (int i = 0; i < SPEEDS.size(); i++){
                SPEEDS.set(i, 0.0);
            }
            runMotors();
            
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    // sets wether or to record
    public static void setRecord(boolean record){
        m_record = record;
    }

    // runs the motors
    public static void runMotors(){
        for (int i = 0; i < MOTORS.size(); i++) {
            MOTORS.get(i).set(SPEEDS.get(i));
        }
    }
}
