grails.project.work.dir = "target"
grails.project.target.level = 1.6

grails.project.dependency.resolution = {
    inherits("global")

    log "warn" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'

    repositories {
        grailsCentral()
    }

    dependencies {
    }

    plugins {
        build(":release:1.0.1") { export = false }
    }
}
