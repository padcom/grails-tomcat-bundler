import grails.util.BuildScope

scriptScope = BuildScope.WAR

includeTargets << grailsScript("_GrailsWar")
includeTargets << grailsScript("_GrailsPackage")

target(prepareDistributionPackage: 'Creates a Zip archive for deployment') {
	depends(checkVersion, configureWarName, war)

	def outputFile = config.deployment.file ?: "${grailsAppName}-${metadata.getApplicationVersion()}.zip"
	def tomcatVersion = config.deployment.tomcat.version ?: "7.0.25"
	def tomcatUrl = config.deployment.tomcat.url ?: "http://ftp.tpnet.pl/vol/d1/apache/tomcat/tomcat-7/v7.0.25/bin/apache-tomcat-7.0.25.zip"

	grailsConsole.updateStatus "Generating deployment package..."

	ant.delete(dir: "target/deployment-creator")
	ant.mkdir(dir: "target/deployment-creator")

	grailsConsole.updateStatus "Retrieving Tomcat bundle..."

	ant.get(
		src : tomcatUrl,
		dest: "target/",
		usetimestamp: true
	)

	grailsConsole.updateStatus "Unzipping Tomcat..."

	ant.unzip(
		src : "target/apache-tomcat-${tomcatVersion}.zip",
		dest: "target/deployment-creator/"
	)

	grailsConsole.updateStatus "Renaming Tomcat folder to ${grailsAppName}..."

	ant.rename(
		src : "target/deployment-creator/apache-tomcat-${tomcatVersion}",
		dest: "target/deployment-creator/${grailsAppName}"
	)

	grailsConsole.updateStatus 'Updating shared classpath to include ${catalina.base}/conf folder...'

	ant.propertyfile(file: "target/deployment-creator/${grailsAppName}/conf/catalina.properties") {
		entry(key: 'shared.loader', operation: '+', value: '${catalina.base}/conf')
	}

	grailsConsole.updateStatus 'Copying WAR to deployment folder...'

	ant.copy(
		file : "${warName}",
		todir: "target/deployment-creator/${grailsAppName}/webapps"
	)

	grailsConsole.updateStatus 'Copying common deployment resources...'

	ant.copy(todir: "target/deployment-creator/${grailsAppName}") {
		fileset(dir: 'deployment/common')
	}

	grailsConsole.updateStatus 'Copying ${grailsEnv} deployment resource overrides...'

	ant.copy(todir: "target/deployment-creator/${grailsAppName}") {
		fileset(dir: "deployment/${grailsEnv}")
	}

	grailsConsole.updateStatus 'Creating deployment archive...'

	ant.zip(
		destfile: "target/${outputFile}",
		basedir : "target/deployment-creator"
	)

	grailsConsole.updateStatus "Done creating deployment archive target/${outputFile} for ${grailsEnv}"
}

setDefaultTarget('prepareDistributionPackage')
