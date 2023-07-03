package com.butterycode.cubefruit.utils;

import org.bukkit.plugin.Plugin;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class resourceStorage {

	private String content;
	private File file = null;
	
	public resourceStorage(Plugin plugin, String filepath, boolean storeLocal) {
		if (storeLocal) {
			plugin.saveResource(filepath, false);
			file = new File(plugin.getDataFolder() + File.separator + filepath);
			
			try (BufferedReader br = new BufferedReader(new FileReader(file))) {
				content = br.lines().collect(Collectors.joining("\n"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			InputStream input = plugin.getResource(filepath);
			content = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8)).lines().collect(Collectors.joining("\n"));
		}
	}

	public String getContent() {
		return content;
	}

	public File getFile() {
		return file;
	}
}
