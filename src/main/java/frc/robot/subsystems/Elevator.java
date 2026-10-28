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
public class Elevator extends SubsystemBase {
  private SparkFlex elevatorMotor1;
  private SparkFlex elevatorMotor2;
  private RelativeEncoder relativeEncoder;
  private SparkClosedLoopController elevatorController;
  private double setpointHeight = 0.0;
  private boolean positionControlEnabled = false;

  private SparkFlexConfig elevatorConfig = new SparkFlexConfig();

public Elevator() {
  elevatorMotor1 = new SparkFlex(10,MotorType,k.kBrushless);
  elevatorMotor2 = new SparkFlex(11,MotorType,k.kBrushless);
  elevatorPIDController = elevatorMotor1.getClosedLoopController();
  elevatorEncoder = elevatorMotor1.getEncoder();


elevatorConfig
  .idleMode(IdleMode.kBrake)
  .voltageCompensation(12.0)
  .smartCurrentLimit(40)
  .inverted(true);

elevatorConfig.armEncoder
  .positionConversionFactor(1.75/5.0)
  .velocityConversionFactor(1.75/5.0/60.0)

  elevatorConfig.closedLoop
  .feedbackSensor(FeedbackSensor.kPrimaryEncoder);
  .p(0.83).i(0).d(0)
  .outputRange(-1, 1)

  elevatorclosedLoop.feedForward
  .kS(0.584).kG(0.366).kV(2.15).kA(0.05)

elevatorConfig.closedLoop.MAXMotionConfig
.cruiseVelocity(0.75)
.maxAcceleration(1.0)
.allowedProfileError(0.01)

private SparkFlexConfig followerConfig = new SparkFlexConfig();

followerConfig
idleMode(IdleMode.kBrake)
.voltageCompensation(12.0)
.smartCurrentLimit(40)
.follow(elevatorMotor1, true)

elevatorMotorController.configure(
 elevatorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters

)


    
  }
  


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    // Not needed for now; hi
  }

}
