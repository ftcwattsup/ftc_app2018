package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class CubeCollector
{
    private Servo upL, upR, downL, downR;
    private DcMotor lift;
    private Telemetry.Item telemetry;
    private boolean verbose = false;
    private int[] openP = {0, 0, 0, 0}; /// upL, upR, downL, downR
    private int[] closeP = {0, 0, 0, 0};
    private int[] liftP = {0, (int)(1e6)};
    private int stateUp = 0, stateDown = 0; /// 0 - open, 1 - closed

    /// TODO: get values for openP, closeP and liftP

    CubeCollector(Servo _upL, Servo _upR, Servo _downL, Servo _downR, DcMotor _lift)
    {
        upL = _upL; upR = _upR; downL = _downL; downR = _downR; lift = _lift;
        verbose = false;
        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        upL.setPosition(openP[0]);
        upR.setPosition(openP[1]);
        downL.setPosition(openP[2]);
        downR.setPosition(openP[3]);
    }

    CubeCollector(Servo _upL, Servo _upR, Servo _downL, Servo _downR, DcMotor _lift, Telemetry.Item _telemetry)
    {
        upL = _upL; upR = _upR; downL = _downL; downR = _downR; lift = _lift;
        telemetry = _telemetry;
        verbose = true;
        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        telemetry.setValue("OK!");

        upL.setPosition(openP[0]);
        upR.setPosition(openP[1]);
        downL.setPosition(openP[2]);
        downR.setPosition(openP[3]);
    }

    public void moveLift(double power)
    {
        if(lift.getCurrentPosition() < liftP[0])   { lift.setPower(0); return; }
        if(lift.getCurrentPosition() > liftP[1])   { lift.setPower(0); return; }

        lift.setPower(power);
    }

    public void setLiftMode(DcMotor.RunMode rm) { lift.setMode(rm); }

    public void changeState(int ids)
    {
        if( (ids & 1) > 0 )
        {
            if(stateUp == 1)
            {
                upL.setPosition(openP[0]);
                upR.setPosition(openP[1]);
            }
            else if(stateUp == 0)
            {
                upL.setPosition(closeP[0]);
                upR.setPosition(closeP[1]);
            }
            stateUp ^= 1;
        }
        if( (ids & 2) > 0 )
        {
            if(stateDown == 1)
            {
                downL.setPosition(openP[2]);
                downR.setPosition(openP[3]);
            }
            else if(stateDown == 0)
            {
                downL.setPosition(closeP[2]);
                downR.setPosition(closeP[3]);
            }
            stateDown ^= 1;
        }
    }
}