# Various ideas and features

## Goals:
- Planning, planning automatization & task control
- Growing good habits

## Tasks for solving:
- Shopping list
- Commonplace tasks
  - Accounting
    - Add
    - Edit
    - Delete
  - Planning
  - Priority
  - Reminders
  - Execution timers
    - Smart timers
  - Task decomposition
  - Fast and easy replanning
  - Personal planning protection
    - from third party
    - crypto key
  - Mobile app
  - Dialogue system

## Task timer & splits
1. Pressing start task button
2. Timer starts
3. You can make splits
4. You can mark splits
5. Expended time is accounted in foregoing planning
6. Voice control
  - Need open source voice assistant
    - [Mycroft](https://mycroft.ai/)

## Perfect scenario for shopping
1. Make shopping list
2. When you enter the store you have shopping list on your screen
    - Optionally the list can sync with store data
    - Buttons
      - Mark bought
      - Take picture (for price and/or object image)
      - Mark not available in this store

## Examples of good habits:
- Sleep
  - Waking up early
  - Get to bed early
- Eating
  - Dedicated cooking and eating time
- Morning
  - Washing eyes
  - Washing teeth
  - Making breakfast & tea
- Working
  - Music to stay focused

## Task structure
- Title
- Time of adding
- Time of start
- Time of end
- Repeatable
- Location
- Habit flag
- Maintenance task flag
- Category
- Parent task
- Subtasks
- Dependencies
- Time for task per day

## Dialogue system
Dialogue system is an interface where you talk with app like with a human. App worries about you, asks questions, cheers and etc.

### Some terms and features
- **IDK** - you can always answer that you don't know something app asks you. It's okay, but usually the app expect you things that you'd better know
- **Control** - one of app goals is to make personal accounting and management easier. So it always tries to ask whether your plans are executed/corrected/declined and etc.

### Examples for various habits
  - Sleep
    - From app:
    	- Wake Up!
    	  - In the morning
    	- When did you wake up?
    	  - Morning, control
    	  - Answers: Time / IDK
    	  - Share button
    	  - Replans history
    	- When did you go to sleep?
    	  - Morning, control
    	  - Answers: Time / IDK
    	  - Replans history
    	- Get to sleep! Now!
    	  - Bedtime
    	  - Answers: No / I can't
    	- Did you go to sleep?
    	  - Bedtime, control
        - Answers: No / I can't
    - To app:
      - I can't fall asleep
        - Response: advices to fall asleep
  - General
    - From app:
      - We didn't talk lately. How are you?
        - When didn't plan too long

### Avatar
- Load images for more pleasant interaction
  - Various emotion images is better


## Why alternatives are not enough:
- Todoist
  - **No template for task**
  - No stopwatch for tasks
- Google Calendar
  - No task duration
  - Tasks, events, reminders are inconsistent
  - No custom task color

## Screens
1. Hello. I'm %AppName%, the open source personal time management tool.
2. What's your real name? Don't worry, it won't leave your device.
3. What time you usually get to sleep?
4. What time you usually wake up?
5. 

## Intro
Need to determine
1. Language
2. Country
3. Name
4. Age
5. Patterns:
  1. Sleep
  2. Eating
  3. Work / Learn / Rest
  4. Maintenance tasks
