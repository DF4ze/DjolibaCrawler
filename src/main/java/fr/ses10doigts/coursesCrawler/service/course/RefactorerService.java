package fr.ses10doigts.coursesCrawler.service.course;

import java.lang.Thread.State;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RefactorerService {

    @Autowired
    private Refactorer refactorer;
    private Thread thread;

    public RefactorerService() {
	thread = new Thread(refactorer);
    }

    public void launchRefactorer() {
	if (thread == null || thread.getState().equals(State.TERMINATED)) {
	    thread = new Thread(refactorer);
	}
	thread.start();
    }

    public void launchRefactorer(Thread t) {
	if (t != null) {
	    refactorer.setFriend(t);
	}
	launchRefactorer();
    }

    public void stopRefactorer() {
	refactorer.stop();
    }

    public Thread getThread() {
	return thread;
    }

}
