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

public class Arm extends SubsystemBase {
  private SparkFlex armMotorController;
  private SparkClosedLoopController armPIDController;
  private RelativeEncoder armEncoder;
  
  private SparkFlexConfig armConfig = new SparkFlexConfig();

  /** Creates a new ExampleSubsystem. */

  public Arm() {
    armMotorController = new SparkFlex(1, MotorType.kBrushless);
    armPIDController = armMotorController.getClosedLoopController();
    armEncoder = armMotorController.getEncoder();

    armConfig
        .idleMode(IdleMode.kBrake)
        .voltageCompensation(12.0) // Makes it so that even when battery drains, it still pumps out duty cycle relative to 12 Volts
        .smartCurrentLimit(40);

    armConfig.closedLoop
        .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
        .p(0.5).i(0).d(0) // Automatically configures slot 0
        .outputRange(-1, 1)
        .positionWrappingEnabled(false); 

    armMotorController.configure(
        armConfig, ResetMode.kNoResetSafeParameters, PersistMode.kNoPersistParameters
    );
    }

    public double getArmAngleDeg(){
        return (armEncoder.getPosition() * (360/50)); // 360 degrees for every 50 rotations
    }

    public void AlignArmToAngle(double angle){
        double targetRotations = angle * (50.0 / 360);
        armPIDController.setSetpoint(targetRotations, ControlType.kPosition, ClosedLoopSlot.kSlot0);
    }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    // Not needed for now
  }

}

