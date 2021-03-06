package org.firstinspires.ftc.teamcode;

import android.support.annotation.Keep;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsAnalogOpticalDistanceSensor;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cColorSensor;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cCompassSensor;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.CompassSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.UltrasonicSensor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import java.security.KeyPair;

@Autonomous(name="Autonomous Middle", group="Linear Opmode")
@Disabled
public class AutonomousMiddle extends AutonomousFunctions {
    @Override

    public void runOpMode() {
        Servo extender;
        extender=hardwareMap.get(Servo.class,"extender");
        extender.setPosition(1.0);
        initialization ();

        if(isStopRequested())   return;

        waitForStart();

        runtime.reset();


        /// Grab cube
        state.setValue("grab cube");
        telemetry.update();
        grab_cube();
        if (isStopRequested()) return;

        /// Score jewels
        state.setValue("score jewels");
        telemetry.update();
        scoreJewels(extender);
        if(isStopRequested())   return;

        /// Gets key drawer
        state.setValue("get key drawer");
        int drawer = getKeyDrawer();
        if(forward == 1)    drawer = 4 - drawer;
        telemetry.update();

        /// Get down from platform
        state.setValue("get down from platform");
        telemetry.update();
        getDown();
        Keep_Orientation(0);
        if(isStopRequested())   return;

        /// Go in front of first drawer
        state.setValue("go to drawer");
        telemetry.update();
        go_to_drawer();
        if (isStopRequested()) return;

        /// Go to needed drawer
        state.setValue("pick drawer");
        telemetry.update();
        pick_drawer(drawer);

        /// Rotate 90 degrees
        state.setValue("rotate");
        telemetry.update();
        Keep_Orientation(270);
        if( isStopRequested() ) return;


        /// Place cube
        state.setValue("place cube");
        telemetry.update();
        //place_cube_encoders(true);
        place_cube_time();
        if( isStopRequested() ) return;
    }
}