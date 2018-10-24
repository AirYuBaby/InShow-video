package com.InShowVideo.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class SheepMergeVideoMp3 {

	private String ffmpegEXE;

	public SheepMergeVideoMp3(String ffmpegEXE) {
		super();
		this.ffmpegEXE = ffmpegEXE;
	}

	public void convertor0(String videoInputPath, String _videoInputPath) throws Exception {
		// ffmpeg -i test.mp4 -vcodec copy -an 视频流.avi
		System.out.println("---------开始保存缓存文件");
		
		System.out.println("---------即将开始执行命令");
	

		// 分割线--------下面是来自网上的代码
		String cmd =ffmpegEXE+" -i " + videoInputPath + " -an -f mp4 " + _videoInputPath;
		final Process process = Runtime.getRuntime().exec(new String[] { "cmd", "/c", cmd });

		System.out.println("---------命令执行，开始读取输出流");

		printMessage(process.getInputStream());
		printMessage(process.getErrorStream());
		int value = process.waitFor();
		System.out.println(value);
		System.out.println("---------缓存文件保存完毕");

	}

	public void convertor(String videoInputPath, String mp3InputPath, double seconds, int position,
			String _videoInputPath, String videoOutputPath) throws Exception {
		System.out.println("---------开始合成");
		mp3InputPath = "\""+mp3InputPath+"\"";
		convertor0(videoInputPath, _videoInputPath);

		String cmd = ffmpegEXE + " -i " + _videoInputPath + " -ss " + String.valueOf(position) + " -i " + mp3InputPath
				+ " -t " + String.valueOf(seconds) + " -y " + videoOutputPath;
		final Process process = Runtime.getRuntime().exec(new String[] { "cmd", "/c", cmd });
		
		System.out.println("---------命令执行，开始读取输出流"+cmd);

		printMessage(process.getInputStream());
		printMessage(process.getErrorStream());
		int value = process.waitFor();
		System.out.println(value);
		System.out.println("---------文件生成完毕");
		final Process process2 = Runtime.getRuntime().exec(new String[] { "cmd", "/c", "del /Q "+_videoInputPath });
		printMessage(process2.getInputStream());
		printMessage(process2.getErrorStream());
		System.out.println("---------缓存文件删除完毕");
	}

	private static void printMessage(final InputStream input) {
		new Thread(new Runnable() {
			public void run() {
				Reader reader = new InputStreamReader(input);
				BufferedReader bf = new BufferedReader(reader);
				String line = null;
				try {
					while ((line = bf.readLine()) != null) {
						System.out.println(line);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

}
