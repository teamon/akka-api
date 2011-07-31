package com.verknowsys.akkaapi

import scala.tools.nsc.{Global, Phase}
import scala.tools.nsc.plugins.{Plugin, PluginComponent}



class AkkaApiPlugin(val global: Global) extends Plugin {
    import global._

    val name = "akka-api"
    val description = "Auto-generate actors specification"

    val components = List[PluginComponent](
        new Generator(this, global)
    )

    class Generator(plugin: AkkaApiPlugin, val global: Global) extends PluginComponent {
        import global._

        val phaseName = AkkaApiPlugin.this.name
        def newPhase(prev: Phase) = new GeneratorPhase(prev)
        val runsAfter = "typer" :: Nil
    }

    class GeneratorPhase(prev: Phase) extends Phase(prev){
        def name = AkkaApiPlugin.this.name
        def run {
            for(unit <- currentRun.units){
                traverse(unit.body)
            }
        }

        def traverse(tree: Tree){
            tree match {
                case pkg: PackageDef => traversePackage(pkg)
                case _ =>
            }
        }

        def traversePackage(pkg: PackageDef){
            // println("Found package: " + pkg.pid)

            for(tree <- pkg.stats){
                tree match {
                    case ClassDef(mods, className, tparams, impl) =>
                        for(body <- impl.body){
                            body match {
                                case DefDef(mods, name, tparams, vparamss, tpt, rhs) =>
                                    if(name.toString == "receive"){
                                        println(className)
                                        rhs match {
                                            case Function(vparams, body) =>
                                                body match {
                                                    case Match(selector, cases) =>
                                                        for(cse <- cases){
                                                            cse.pat match {
                                                                case Select(qualifier, name) =>
                                                                    println(" > " + qualifier + "." + name)
                                                                case Apply(fun, args) =>
                                                                    fun match {
                                                                        case tt: TypeTree =>
                                                                            println(" > " + tt.original)
                                                                        case _ =>
                                                                    }
                                                                case Bind(name, body) =>
                                                                    // println(" > " + name)
                                                                case _ =>
                                                            }
                                                        }
                                                    case _ =>
                                                }
                                            case _ =>
                                        }
                                    }
                                case _ =>
                            }
                        }
                    case _ =>
                }
            }
        }
    }
}
