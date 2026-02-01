package com.royalhackaway.checkmatedungeon;

public final class AppStarter {
	// Minimal, deterministic entrypoint so docker can run the container even when the
	// built jar doesn't include a Main-Class in the manifest.
	public static void main(String[] args) {
		System.out.println("AppStarter: packaged jar does not include a Main-Class manifest entry.");
		System.out.println("If you expect a runnable application, run it with a fully-qualified main:");
		System.out.println("  java -cp app.jar com.your.MainClass");
		System.out.println("Or use Maven to run a class:");
		System.out.println("  mvn -q -Dexec.mainClass=com.royalhackaway.checkmatedungeon.service.GameService exec:java");
	}
}
