package dsl

import org.junit.Test
import com.cloudbees.plugins.flow.dsl.FlowDSL
import hudson.model.Cause

public class DSLTest {

	def script = """
	flow {
	    println "\\nTriggered by " + cause + "\\n"

	    a = build("Job1")
	    b = build("Job2", param1: "troulala", param2: "machin")
	    c = build("Job3")

	    println "\\n" + a.result() + " " + b.result() + " " + c.result() + "\\n"

	    par = parallel {
	        build("jobp1")
	        build("jobp2")
	    }

	    println ""
	    par.values().each { job -> println job.result() }
	    println ""

        build("job3")

	    guard {
	        build("job1")
	        build("job2")
	    } rescue {
	        build("job3")
	    }
    }
	"""

    def String getScript() {
        return script;
    }

    @Test
    public void testParseDSL() {
        new FlowDSL().executeFlowScript(script, null)
    }

    public void testParseDSL(Cause cause) {
        new FlowDSL().executeFlowScript(script, cause)
    }
}
