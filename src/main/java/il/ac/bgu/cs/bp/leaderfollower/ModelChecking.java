package il.ac.bgu.cs.bp.leaderfollower;

import il.ac.bgu.cs.bp.bpjs.analysis.BProgramStateVisitedStateStore;
import il.ac.bgu.cs.bp.bpjs.analysis.DfsBProgramVerifier;
import il.ac.bgu.cs.bp.bpjs.analysis.VerificationResult;
import il.ac.bgu.cs.bp.bpjs.analysis.VisitedStateStore;
import il.ac.bgu.cs.bp.bpjs.analysis.listeners.BriefPrintDfsVerifierListener;
import il.ac.bgu.cs.bp.bpjs.model.BProgram;
import il.ac.bgu.cs.bp.bpjs.model.SingleResourceBProgram;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

/**
 * This class model-checks the rover control b-program.
 * 
 * @author michael
 */
public class ModelChecking {
    
    
    public static void main(String[] args) throws Exception {
        new ModelChecking().start();
    }
    
    public void start() throws Exception {
        // create the compound model
        BProgram model = new SingleResourceBProgram("ControllerLogic.js");
        model.prependSource( readResource("SimulatedEnvironment.js") );
        model.prependSource( readResource("ModelAssertions.js") );
        
        // Create the verifier
        DfsBProgramVerifier vfr = new DfsBProgramVerifier();
        vfr.setMaxTraceLength(300);
        vfr.setProgressListener( new BriefPrintDfsVerifierListener() );
        vfr.setVisitedNodeStore( new BProgramStateVisitedStateStore(false) );
        
//        vfr.setDetectDeadlocks(false); // Should go away in next version
        
        VerificationResult verificationResult = vfr.verify(model);
        
        if ( verificationResult.isCounterExampleFound() ) {
            System.out.println("Counter example found. Type: " + verificationResult.getViolationType());
            if ( verificationResult.getFailedAssertion() != null ) {
                System.out.println("Verification message: " + verificationResult.getFailedAssertion().getMessage());
            }
            
            verificationResult.getCounterExampleTrace().forEach( e -> System.out.println(e) );
        } else {
            System.out.println("No counter example found.");
        }
    }
    
    
    private String readResource( String resourceName ) throws IOException { 
        try ( InputStream resource = Thread.currentThread().getContextClassLoader().getResourceAsStream(resourceName);
              BufferedReader rdr = new BufferedReader(new InputStreamReader(resource))) {
            if (resource == null) {
                throw new RuntimeException("Resource '" + resourceName + "' not found.");
            }
            return rdr.lines().collect( Collectors.joining("\n") );
            
        } catch (IOException ex) {
            throw new RuntimeException("Error reading resource: '" + resourceName +"': " + ex.getMessage(), ex);
        }
    }
}
