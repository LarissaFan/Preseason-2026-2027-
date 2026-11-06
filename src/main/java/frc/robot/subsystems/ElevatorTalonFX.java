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
public class ElevatorTalonFX extends SubsystemBase{

private static final double METERS_PER_MOTOR_ROTATION = 2.0 * Math.PI * Units.inchesToMeters(1.75) / 5.0;
MAX_HEIGHT_METERS = 1.44;
private final TalonFX elevatorMotor1;
private final TalonFX elevatorMotor2;
private final MotionMagicVoltage positionRequest = new MotionMagicVoltage(0.0).withSlot(0); 
private final DutyCycleOut speedRequest = new DutyCycleOut(0.0);
private final VoltageOut voltageRequest = new VoltageOut(0.0);
private double setpointHeight = 0.0;
private boolean positionControlEnabled = false;
private double PeakReverseVoltage;
private boolean StatorCurrentLimitEnable;

  public ElevatorTalonFX() {
  elevatorMotor1 = new TalonFX(10);
  elevatorMotor2 = new TalonFX(11);

  TalonFXConfiguration leaderConfig = new TalonFXConfiguration();
  leaderConfig.MotorOutput.NeutralMode = NeutralModeValue.Brake;
  leaderConfig.MotorOutput.Inverted = InvertedValue.Clockwise_Positive;
  leaderConfig.CurrentLimits.StatorCurrentLimit = 40.0;
  leaderConfig.CurrentLimits.StatorCurrentLimitEnable = True;

  leaderConfig.Voltage.PeakForwardVoltage = 12.0;
  leaderConfig.Voltage.PeakReverseVoltage = -12.0;

  leaderConfig.Feedback.SensorToMechanismRatio = 1.0;
  leaderConfig.Feedback.RotorToSensorRatio = 1.0;

  leaderConfig.Slot0.kP = 0.83 * 12.0 * METERS_PER_MOTOR_ROTATION;
  leaderConfig.Slot0.kI = 0.0;
  leaderConfig.Slot0.kD = 0.0;

  leaderConfig.Slot0.kS = 0.584;
  leaderConfig.Slot0.kG = 0.366; 
  leaderConfig.Slot0.kV = 2.15 * METERS_PER_MOTOR_ROTATION;
  leaderConfig.Slot0.kA = 0.05 * METERS_PER_MOTOR_ROTATION;

  leaderConfig.Slot0.GravityType = GravityTypeValue.Elevator_Static;

  leaderConfig.MotionMagic.MotionMagicCruiseVelocity = 0.75 / METERS_PER_MOTOR_ROTATION
  leaderConfig.MotionMagic.MotionMagicAcceleration = 1.0 / METERS_PER_MOTOR_ROTATION;
  leaderConfig.MotionMagic.MotionMagicJerk = 0.0;

  TalonFXConfiguration followerConfig = new TalonFXConfiguration();
  followerConfig.MotorOutput.NeutralMode = NeutralModeValue.Brake;
  followerConfig.CurrentLimits.StatorCurrentLimit = 40.0
  followerConfig.CurrentLimits.StatorCurrentLimitEnable = true
  followerConfig.Voltage.PeakForwardVoltage = 12.0
  followerConfig.Voltage.PeakReverseVoltage = -12.0;

  elevatorMotor1.getConfigurator().apply(leaderConfig);
  elevatorMotor2.getConfigurator().apply(followerConfig);
  elevatorMotor1.getDutyCycle().setUpdateFrequency(50.0);
  elevatorMotor1.getMotorVoltage().setUpdateFrequency(50.0);

  elevatorMotor2.setControl(new Follower(elevatorMotor1.getDeviceID(), MotorAlignmentValue.Opposed));

  elevatorMotor1.setPosition(0.0);

  public double getElevatorHeight(){

  elevatorMotor1.getPosition().getValueAsDouble() * METERS_PER_MOTOR_ROTATION;

  }

  public double getElevatorVelocity(){

  elevatorMotor1.getVelocity().getValueAsDouble() * METERS_PER_MOTOR_ROTATION;

  }

  public void setDesiredHeight(double heightMeters)
  
  setpointHeight = MathUtil.clamp(heightMeters, 0.0, MAX_HEIGHT_METERS);
  elevatorMotor1.setControl(positionRequest.withPosition(setpointHeight / METERS_PER_MOTOR_ROTATION));
  positionControlEnabled = true;

  public void setElevatorSpeed(double speed)
  positionControlEnabled = false;
  elevatorMotor1.setControl(speedRequest.withOutput(MathUtil.clamp(speed, -1.0, 1.0)));

  public void setElevatorVoltage(double voltage)
  positionControlEnabled = false;
  elevatorMotor1.setControl(voltageRequest.withOutput(MathUtil.clamp(voltage, -12.0, 12.0)));

  public void stop()
  positionControlEnabled = false;
  evatorMotor1.stopMotor();

  public void zeroEncoder()
  stop(); then elevatorMotor1.setPosition(0.0); 
  setpointHeight = 0.0;

  public boolean isAtSetpoint()
  positionControlEnabled && Math.abs(getElevatorHeight() - setpointHeight) < Units.inchesToMeters(1);
  




  }



  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    // Not needed for now;
  }

}
