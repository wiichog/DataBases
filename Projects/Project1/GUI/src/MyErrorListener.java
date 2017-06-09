
import java.util.ArrayList;
import java.util.BitSet;

import javax.swing.text.html.HTMLEditorKit.Parser;

import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.Token;
import org.antlr.v4.codegen.model.Recognizer;
import org.antlr.v4.codegen.model.TestSetInline.Bitset;
import org.antlr.v4.parse.ANTLRLexer;
import org.antlr.v4.runtime.DiagnosticErrorListener;
import org.antlr.v4.runtime.atn.ATNConfigSet;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.Interval;
import org.antlr.v4.runtime.*;

public class MyErrorListener implements ANTLRErrorListener
{
	
	
    private  ArrayList<String> _errorMessages = new ArrayList<String>();
    public ArrayList<String> ErrorMessages = _errorMessages;

    private  ArrayList<String> _warningMessages = new ArrayList<String>();
    public ArrayList<String> WarningMessages = _warningMessages;
   

    public boolean HasErrors = ErrorMessages.size() > 0; 
    public boolean HasWarnings = WarningMessages.size() > 0; 

    private  boolean _captureDiagnostics;

    
    
/*
    public  void ReportAmbiguity(org.antlr.v4.runtime.Parser recognizer, org.antlr.v4.runtime.dfa.DFA dfa, int startIndex, int stopIndex, boolean exact, BitSet ambigAlts, org.antlr.v4.runtime.atn.ATNConfigSet configs)
    {
        if (_captureDiagnostics)
        {
            _warningMessages.add(String.format("reportAmbiguity d={0}: ambigAlts={1}, input='{2}'", getDecisionDescription(recognizer, dfa), getConflictingAlts(ambigAlts, configs), ((TokenStream)recognizer).getText(Interval.of(startIndex, stopIndex))));
        }
    }

    public  void ReportAttemptingFullContext(Parser recognizer, org.antlr.v4.runtime.dfa.DFA  dfa, int startIndex, int stopIndex, BitSet conflictingAlts, org.antlr.v4.runtime.atn.simulatorState conflictState)
    {
        if (_captureDiagnostics)
        {
            _warningMessages.Add(string.Format("reportAttemptingFullContext d={0}, input='{1}'", getDecisionDescription(recognizer, dfa), ((TokenStream)recognizer.InputStream).GetText(Interval.of(startIndex, stopIndex))));
        }
    }

    public override void ReportContextSensitivity(Parser recognizer, Antlr4.Runtime.Dfa.DFA dfa, int startIndex, int stopIndex, int prediction, Antlr4.Runtime.Atn.SimulatorState acceptState)
    {
        if (_captureDiagnostics)
        {
            _warningMessages.Add(string.Format("reportContextSensitivity d={0}, input='{1}'", GetDecisionDescription(recognizer, dfa), ((ITokenStream)recognizer.InputStream).GetText(Interval.of(startIndex, stopIndex))));
        }
    }

*/


	@Override
	public void reportAmbiguity(org.antlr.v4.runtime.Parser arg0, DFA arg1, int arg2, int arg3, boolean arg4,
			BitSet arg5, ATNConfigSet arg6) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void reportAttemptingFullContext(org.antlr.v4.runtime.Parser arg0, DFA arg1, int arg2, int arg3, BitSet arg4,
			ATNConfigSet arg5) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void reportContextSensitivity(org.antlr.v4.runtime.Parser arg0, DFA arg1, int arg2, int arg3, int arg4,
			ATNConfigSet arg5) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void syntaxError(org.antlr.v4.runtime.Recognizer<?, ?> arg0, Object arg1, int arg2, int arg3, String arg4,
			org.antlr.v4.runtime.RecognitionException arg5) {
		
    	ErrorMessages.add(String.format("Error en  %d:%d %s ", arg2, arg3, arg4));
    	HasErrors = (ErrorMessages.size() > 0); 
		
	}
}