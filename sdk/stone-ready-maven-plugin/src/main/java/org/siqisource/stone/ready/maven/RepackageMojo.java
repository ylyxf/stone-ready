package org.siqisource.stone.ready.maven;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;

/**
 * 这个类的作用是对war包进行重新打包 从里面挑出标记了指定注解的类，将它们重新打包。 还没有想好怎么做
 * 
 * @author yulei
 *
 */
@Mojo(name = "repackage", defaultPhase = LifecyclePhase.PACKAGE, requiresProject = true, threadSafe = true, requiresDependencyResolution = ResolutionScope.COMPILE_PLUS_RUNTIME, requiresDependencyCollection = ResolutionScope.COMPILE_PLUS_RUNTIME)
public class RepackageMojo extends AbstractMojo {

	@Parameter(defaultValue = "${project}", readonly = true, required = true)
	private MavenProject project;

	@Parameter
	private String classifier;

	@Parameter(defaultValue = "${project.build.outputDirectory}", required = true)
	private File outputDirectory;

	@Parameter(defaultValue = "${project.build.sourceDirectory}", required = true)
	private File sourceDirectory;

	@Parameter
	private String exportName;

	public void execute() throws MojoExecutionException, MojoFailureException {
		if (this.project.getPackaging().equals("pom")) {
			getLog().debug("repackage goal could not be applied to pom project.");
			return;
		}
		File target = getTargetFile();
		Set<File> distFiles = new HashSet<File>();
		try {
			initDistFiles(outputDirectory, distFiles);

		} catch (

		Exception e) {
			e.printStackTrace();
		}

	}

	private void initDistFiles(File parent, Set<File> distFiles) {

		for (File file : parent.listFiles()) {
			if (file.isDirectory()) {
				initDistFiles(file, distFiles);
			} else {
				if (!file.getName().endsWith(".java")) {
					continue;
				}

			}
		}
	}

	private boolean isDistFile(File file) {
		return false;
	}

	private File getTargetFile() {
		String classifier = (this.classifier != null) ? this.classifier.trim() : "";
		if (!classifier.isEmpty() && !classifier.startsWith("-")) {
			classifier = "-" + classifier;
		}
		if (!this.outputDirectory.exists()) {
			this.outputDirectory.mkdirs();
		}
		String finalName = this.project.getBuild().getFinalName();
		return new File(this.outputDirectory, finalName + classifier + ".jar");
	}

}
