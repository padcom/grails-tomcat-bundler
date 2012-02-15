class TomcatBundlerGrailsPlugin {
    def version = "0.0.1"
    def grailsVersion = "2.0 > *"
    def pluginExcludes = [
		"grails-app/views/error.gsp"
	]

    def title = "Tomcat application bundler plugin"
    def author = "Matthias Hryniszak"
    def authorEmail = "padcom@gmail.com"
    def description = 'A plugin to simplify bundling application with Tomcat to create a whole package to be used by hosting'
    def documentation = "http://grails.org/plugin/tomcat-bundler"

    def license = "APACHE"
    def issueManagement = [ system: "GitHub", url: "http://github.com/padcom/grails-tomcat-bundler/issues" ]
    def scm = [ url: "http://github.com/padcom/grails-tomcat-bundler" ]
}
