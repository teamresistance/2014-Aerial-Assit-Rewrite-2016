---
layout: post
title: "What & Why: The Snorfler Subsystem"
---

The Snorfler picks up balls from the floor and hurls them into the interior Bin. It has an open top, so it can also coerce balls from the hopper into the Bin.

<!--more-->

## Vocabulary

The system is the **snorfler**. Spin the motor forward to **snorfle**. Spin it backward to **reverse**. Stop the motor to turn the snorfler **off**.

## Requirements

<div class="mermaid">
graph LR
  A((Reversing))
  B((Stopped))
  C((Snorfling))

  A -->|Lift Reverse| B
  A -->|Toggle| C
  B -->|Hold Reverse| A
  B -->|Toggle| C
  C -->|Toggle| B
</div>

You will need to register two buttons on the codriver joystick.

1. Driver presses **Toggle** to snorfle while the snorfler is off.
2. Driver presses **Toggle** to stop while the snorfler is snorfling.
3. When driver holds **Reverse** AND the snorfler is off, reverse the snorfling.
4. Stop reversing when either **Reverse** is released OR **Toggle** is pressed (meaning the snorfler is snorfling).

The snorfler starts in the *off* state.

## Hardware

##### Inputs
*No hardware inputs.*

##### Outputs

* **Snorfler motor**: single speed, bi-directional
   * Speed set during testing, not driver adjustable

## Details

Writing the code for the snorfler is pretty much a one-man job. Once the code has been merged it, it'll be ready to immediately test on the new robot without any tuning because we typically just run subsystem motors at full speed.

We're currently looking at three two: SnorflerToggle, SnorflerReverse. We may not need to dispatch a SnorflerStop command when the Reverse button is lifted because we can stop the motor in the cleanup method of SnorflerReverse. The Strongback `SwitchReactor` will handle the button press and press-and-hold behaviors. The command dispatcher will take care of cancelling the running command when another command is dispatched for the same motor as long as that motor has been registered as the Command's `Requireable` (see the docs).

The Snorfler isn't completely dumb. There are particular state conditions we can't violate. For example, we shouldn't be able to reverse while the snorfler is snorfling. The commands are currently responsible for ensuring their preconditions hold true before setting the motor outputs, which may be done through a simple `if` check. Suchs checks constitue the "logic" of the snorfler. If we find the logic is too complex to be included in every command, then we can write an intermediate `Snorfler` class with methods for reverse/snorfle that contain all the logic and set the outputs. The commands would call these methods rather than setting the outputs themselves.

Pay special heed to requirements 3 and 4. We haven't found a reliable way to implement press-and-hold, or maybe we have but our usage was wrong. Read the Strongback docs for this one. We'll be sure to cover this particular interaction in our Test Plan.

### Progress: 02/09/17

Tarik has a [pull request](https://github.com/teamresistance/fist-of-life/pull/16) that contains a SnorfleCommand that runs forever based on the old requirement. He'll need to update what he has to follow the new details, or start anew. This task is unassigned until we verify who intends to work on it.

## Testing Plan

1. Pressing Toggle will spin the motor forward if it is idle
2. Pressing Toggle will stop the motor if it is spinning forward
3. Pressing Toggle will spin the motor forward if it is spinning backward
4. Pressing and holding Reverse will spin the motor backward if it is idle
5. Pressing and holding Reverse is ignored if the motor is spinning forward
6. Releasing Reverse will stop the motor if it is spinning backward
7. **Edge case:** Pressing Toggle while holding Reverse will spin the motor forward. Releasing Reverse will have no effect.
