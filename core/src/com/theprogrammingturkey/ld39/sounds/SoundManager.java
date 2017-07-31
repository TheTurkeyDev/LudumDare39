package com.theprogrammingturkey.ld39.sounds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.theprogrammingturkey.ld39.game.Settings;

public class SoundManager
{
	public static final Sound BUTTON_PRESS = Gdx.audio.newSound(Gdx.files.internal("sounds/button.wav"));
	public static final Sound ROBOT_PLACE = Gdx.audio.newSound(Gdx.files.internal("sounds/robot_place.wav"));
	public static final Sound ROBOT_SELECT = Gdx.audio.newSound(Gdx.files.internal("sounds/robot_select.wav"));
	public static final Sound UNDO = Gdx.audio.newSound(Gdx.files.internal("sounds/undo.wav"));

	public static void playSound(Sound sound, float volume)
	{
		if(Settings.sounds)
			sound.play(volume);
	}

	public static void disposeSounds()
	{
		BUTTON_PRESS.dispose();
		ROBOT_PLACE.dispose();
		ROBOT_SELECT.dispose();
		UNDO.dispose();
	}
}
