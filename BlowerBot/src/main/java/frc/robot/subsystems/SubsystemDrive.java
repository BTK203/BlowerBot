/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Constants;
import frc.robot.util.Xbox;
import frc.robot.commands.ManualCommandDrive;

/**
 * The thing that makes the wheels move bro
 */
public class SubsystemDrive extends Subsystem {
  
  private TalonSRX
    leftMaster,
    leftSlave,
    rightMaster,
    rightSlave;


  public SubsystemDrive() {
    leftMaster  = new TalonSRX(Constants.DRIVE_LEFT_MASTER_ID);
    leftSlave   = new TalonSRX(Constants.DRIVE_LEFT_SLAVE_ID);
    rightMaster = new TalonSRX(Constants.DRIVE_RIGHT_MASTER_ID);
    rightSlave  = new TalonSRX(Constants.DRIVE_RIGHT_SLAVE_ID);
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new ManualCommandDrive());
  }

  public void drive(Joystick joy) {
    double throttle = Xbox.RT(joy) - Xbox.LT(joy);
    double steering = Xbox.LEFT_X(joy);

    double driveRight = throttle + steering;
    double driveLeft = throttle - steering;

    driveRight = (driveRight < 0 ? 0 : (driveRight > 1 ? 1 : driveRight));
    driveLeft = (driveLeft < 0 ? 0 : (driveLeft > 1 ? 1 : driveLeft));

    SetMotors(driveLeft, driveRight);
  }

  public void SetMotors(double left, double right) {
    leftMaster.set(ControlMode.PercentOutput, left);
    leftSlave.set(ControlMode.PercentOutput, left);
    rightMaster.set(ControlMode.PercentOutput, right);
    rightSlave.set(ControlMode.PercentOutput, right);
  }
}
