package frc.robot.subsystems;

import com.revrobotics.PersistMode;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.ClosedLoopSlot;
import com.revrobotics.spark.FeedbackSensor;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkFlexConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {
private final TalonFX pivotMotor;
private final TalonFX rollerMotor;
private final VoltageOut rollerVoltageRequest = new VoltageOut(0.0);
private final MotionMagicVoltage pivotPositionRequest = new MotionMagicVoltage(0.0).withSlot(0); 
private final VoltageOut pivotVoltageRequest = new VoltageOut(0.0);
private static final double METERS_PER_MOTOR_ROTATION = 2.0 * Math.PI / 20;
private static final double MAX_POSITION = Math.PI/2;

    public Intake() {
        rollerMotor = new TalonFX(12)
        pivotMotor = new TalonFX(1)
    TalonFXConfiguration rollerConfig = new TalonFXConfiguration();
        rollerConfig.MotorOutput.NeutralMode = NeutralModeValue.Coast;
        rollerConfig.CurrentLimits.StatorCurrentLimit = 40.0;
        rollerConfig.CurrentLimits.StatorCurrentLimitEnable = True;
        rollerConfig.Voltage.PeakForwardVoltage = 12.0;
        rollerConfig.Voltage.PeakReverseVoltage = -12.0;

        TalonFXConfiguration pivotConfig = new TalonFXConfiguration();
        pivotConfig.MotorOutput.NeutralMode = NeutralModeValue.Brake;
        pivotConfig.CurrentLimits.StatorCurrentLimit = 40.0;
        pivotConfig.CurrentLimits.StatorCurrentLimitEnable = True;
        pivotConfig.Voltage.PeakForwardVoltage = 12.0;
        pivotConfig.Voltage.PeakReverseVoltage = -12.0;

        pivotConfig.Slot0.kP = 0.83 * 12.0 * METERS_PER_MOTOR_ROTATION;
        pivotConfig.Slot0.kI = 0.0;
        pivotConfig.Slot0.kD = 0.0;

        pivotConfig.Slot0.kS = 0.584;
        pivotConfig.Slot0.kG = 0.366; 
        pivotConfig.Slot0.kV = 2.15 * METERS_PER_MOTOR_ROTATION;
        pivotConfig.Slot0.kA = 0.05 * METERS_PER_MOTOR_ROTATION;

        rollerMotor.getConfigurator().apply(rollerConfig);
        pivotMotor.getConfigurator().apply(pivotConfig);
     }

    public double getPivotPosition(){
        pivotMotor.getPosition().getValueAsDouble() * METERS_PER_MOTOR_ROTATION;
    }
        

    public double getPivotVoltage(){
        pivotPosition.getVoltage().getValueAsDouble() * METERS_PER_MOTOR_ROTATION;
    }

    public double getRollerVoltage(){
        rollerPosition.getVoltage().getValueAsDouble() * METERS_PER_MOTOR_ROTATION;
    }

    public void setDesiredPosition(double positionMeters){
        setpointPosition = MathUtil.clamp(positionMeters, 0.0, MAX_POSITION);
        rollerMotor.setControl(pivotPositionRequest.withPosition(setpointPosition / METERS_PER_MOTOR_ROTATION));
    }

    public void setRollerVoltage(double voltage){
        rollerMotor.setControl(rollerVoltageRequest.withOutput(MathUtil.clamp(voltage, -12.0, 12.0)));
    }

    public void setPivotVoltage(double voltage){
        pivotMotor.setControl(pivotVoltageRequest.withOutput(MathUtil.clamp(voltage, -12.0, 12.0)));
    }

    public void pivotStop(){
        pivot.Motor.stopMotor();
    }

    public void rollerStop(){
        rollerMotor.stopMotor();
    }

    public void zeroEncoder(){
    stop(); then pivotMotor.setPosition(0.0); 
    setpointHeight = 0.0;
    }


 

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    // Not needed for now
  }

}