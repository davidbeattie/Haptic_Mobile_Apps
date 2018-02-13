package com.orange.musicplayer;

import java.io.Serializable;

/** This class provides a number of variables for use between
  	PlayStopControls activity and MusicList **/

public class MusicItems implements Serializable {
	
	String name;
	String artist;
	int artwork;
	int music;
	int haptics;
	
	public MusicItems(String n, String a, int art, int mus, int hap){
		name = n;
		artist = a;
		artwork = art;
		music = mus;
		haptics = hap;
	}
}
