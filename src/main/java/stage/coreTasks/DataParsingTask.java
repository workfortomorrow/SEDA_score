package stage.coreTasks;

import stage.StageContains;
import stage.tasks.AbstractTask;
import stage.tasks.TaskException;

import javax.management.modelmbean.XMLParseException;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.InputStream;
import java.util.concurrent.CountDownLatch;

/**
 * @Author: duhongjiang
 * @Date: Created in 2018/6/4
 */
public class DataParsingTask extends AbstractTask {

    private InputStream  inputStream;

    public DataParsingTask(InputStream inputStream){
        this.inputStream=inputStream;
    }

    @Override
    protected void  doRun() throws TaskException{

        try {

            XMLEventReader handler = XMLInputFactory.newFactory().createXMLEventReader(inputStream);
            while(handler.hasNext()){
                XMLEvent event = handler.nextEvent();
                if(event.getEventType() == XMLEvent.START_ELEMENT){
                    StartElement startElement = event.asStartElement();
                    if("rating".equals(startElement.getName().getLocalPart().toString())){
                        System.out.println(startElement.getAttributes().toString());
                    }
                }
            }
            System.out.println("finished process xml");

        }catch (XMLStreamException e){
            throw  new TaskException(e);
        }
    }

    @Override
    protected void onTaskSuccess(){
        super.onTaskSuccess();
        getAttribute(StageContains.DLOAD_PARSE_COUNT_DOWN, CountDownLatch.class).countDown();
    }
}
