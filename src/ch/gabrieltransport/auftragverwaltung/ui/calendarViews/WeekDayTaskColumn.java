
package ch.gabrieltransport.auftragverwaltung.ui.calendarViews;

import java.time.LocalDateTime;
import java.util.List;

import com.vaadin.event.ContextClickEvent;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnGenerator;
import com.vaadin.ui.Window;
import com.xdev.ui.XdevButton;
import com.xdev.ui.XdevGridLayout;
import com.xdev.ui.XdevHorizontalLayout;
import com.xdev.ui.XdevHorizontalSplitPanel;
import com.xdev.ui.XdevVerticalLayout;
import com.xdev.ui.entitycomponent.table.XdevTable;

import ch.gabrieltransport.auftragverwaltung.business.TimeHelper;
import ch.gabrieltransport.auftragverwaltung.business.refresher.Broadcaster;
import ch.gabrieltransport.auftragverwaltung.dal.FahrzeugauftragDAO;
import ch.gabrieltransport.auftragverwaltung.entities.Fahrzeug;
import ch.gabrieltransport.auftragverwaltung.entities.Fahrzeugauftrag;
import ch.gabrieltransport.auftragverwaltung.ui.taskDetail;
import ch.gabrieltransport.auftragverwaltung.ui.calendar.CurrentWeek;
import ch.gabrieltransport.auftragverwaltung.ui.taskDetail.Callback;

public class WeekDayTaskColumn extends XdevHorizontalLayout{

	public static class Generator implements ColumnGenerator {
		@Override
		public Object generateCell(Table table, Object itemId, Object columnId) {

			return new WeekDayTaskColumn(table, itemId, columnId);
		}
	}

	private final Table customizedTable;
	private final Object itemId;
	private final Object columnId;
	

	private WeekDayTaskColumn(Table customizedTable, Object itemId, Object columnId) {
		super();

		this.customizedTable = customizedTable;
		
		this.itemId = itemId;
		this.columnId = columnId;

		this.initUI();
		update();	
	}

	public Table getTable() {
		return customizedTable;
	}

	public Object getItemId() {
		return itemId;
	}

	public Object getColumnId() {
		return columnId;
	}

	@SuppressWarnings("unchecked")
	public Fahrzeug getBean() {
		return ((XdevTable<Fahrzeug>) getTable()).getBeanContainerDataSource().getItem(getItemId()).getBean();
	}

	
	private void selectItem() {
		getTable().select(getItemId());
	}
	
	private LocalDateTime getDateForDay(){
		int dayOfWeekOffset = 0;
		switch(getColumnId().toString()){
		case "Montag":
			dayOfWeekOffset = 0;
			break;
		case "Dienstag":
			dayOfWeekOffset = 1;
			break;
		case "Mittwoch":
			dayOfWeekOffset = 2;
			break;
		case "Donnerstag":
			dayOfWeekOffset = 3;
			break;
		case "Freitag":
			dayOfWeekOffset = 4;
			break;
		case "Samstag":
			dayOfWeekOffset = 5;
			break;
		case "Sonntag":
			dayOfWeekOffset = 6;
			break;
		}
		return (new CurrentWeek()).getCurrentWeek().getStartDate().plusDays(dayOfWeekOffset);
	}

	/**
	 * Event handler delegate method for the {@link XdevHorizontalLayout}.
	 *
	 * @see ContextClickEvent.ContextClickListener#contextClick(ContextClickEvent)
	 * @eventHandlerDelegate Do NOT delete, used by UI designer!
	 */
	private void this_contextClick(ContextClickEvent event) {
		Window win = new Window();
		taskDetail taskWindow = new taskDetail(getDateForDay(), getBean(),
				   new taskDetail.Callback() {
				      public void onDialogResult(boolean result) {
				    	  //GuiListenerSingleton.getInstance().updated();
				    	  Broadcaster.broadcast("ALL");
				      }
				   });
		win.setWidth("820");
		win.setHeight("950");
		win.center();
		
		win.setModal(true);
		win.setContent(taskWindow);
		this.getUI().addWindow(win);
	}
	public void update() {
		tasksDaylong.removeAllComponents();
		tasksMorning.removeAllComponents();
		tasksAfternoon.removeAllComponents();
		tasksAfternoon.setStyleName("borderLeft");
		final FahrzeugauftragDAO auftragServiceFacade = new FahrzeugauftragDAO();
		List<Fahrzeugauftrag> auftraege = auftragServiceFacade.findAuftrageon(getDateForDay(), getBean());
		
		for(Fahrzeugauftrag auftrag: auftraege){
			XdevButton auftragField = new XdevButton();
			auftragField.setCaption(auftrag.getAuftrag().getBezeichung());
			auftragField.setHtmlContentAllowed(true);
			StringBuilder sb = new StringBuilder();
			String beschreibung = auftrag.getAuftrag().getBeschreibung().replace("\n", "<br />");
			sb.append("<table><tr><td>" + beschreibung + "</td><td style='border-left:2px solid white'>");
			sb.append("<table>");
			auftrag.getAuftrag().getFahrerauftrags().
				forEach(s -> sb.append("<tr><td>" + s.getFahrer().getVorname() + 
						"</td><td>"+ s.getFahrer().getNachname()+ "</td></tr>"));
			if(auftrag.getAnhaenger() != null){
				sb.append("<tr></tr>");
				sb.append("<tr><td colspan='2'>Anhänger: " + auftrag.getAnhaenger().getNummer() + "</td></tr>");
			}
			sb.append("</table>");
			sb.append("</td></tr></table>");
			auftragField.setDescription(sb.toString());
			auftragField.addStyleName("borderless");
			if(auftrag.getGarage()){
				auftragField.setStyleName("garage");
			}
			if(auftrag.getUmzug()){
				if(auftrag.getMoebellift()){
					auftragField.setStyleName("moebellift");
				}
				else{
					auftragField.setStyleName("umzug");
				}
			}
			
			auftragField.setSizeFull();
			auftragField.addClickListener(new ClickListener() {
				
				@Override
				public void buttonClick(Button.ClickEvent event) {
					Window win = new Window();
					win.setWidth("820");
					win.setHeight("950");
					win.center();
					
					win.setModal(true);
					Fahrzeugauftrag test = auftragServiceFacade.find(auftrag.getIdfahrzeugauftrag());
					taskDetail taskWindow = new taskDetail(test,
							   new taskDetail.Callback() {
							      public void onDialogResult(boolean result) {
							    	  update();
							    	  //GuiListenerSingleton.getInstance().updated();
							    	  Broadcaster.broadcast("ALL");
							    	  
							      }
							   });
					win.setContent(taskWindow);
					getUI().addWindow(win);
					
				}
			});
			LocalDateTime start = auftrag.getVonDatum(); LocalDateTime end = auftrag.getBisDatum();	
			boolean added = false;
			if(TimeHelper.isMorning(start, end)){
				tasksMorning.addComponent(auftragField);
				added = true;
			}
			if(TimeHelper.isAfternoon(start, end)){
				tasksAfternoon.addComponent(auftragField);
				added = true;
			}
			if(!added){
				tasksDaylong.addComponent(auftragField);
			}
		}
		
	}
	
	/*
	 * WARNING: Do NOT edit!<br>The content of this method is always regenerated by
	 * the UI designer.
	 */
	// <generated-code name="initUI">
	private void initUI() {
		this.gridLayout = new XdevGridLayout();
		this.horizontalLayout = new XdevHorizontalLayout();
		this.tasksMorning = new XdevVerticalLayout();
		this.tasksAfternoon = new XdevVerticalLayout();
		this.tasksDaylong = new XdevVerticalLayout();
	
		this.setSpacing(false);
		this.setMargin(new MarginInfo(false));
		this.gridLayout.setSpacing(false);
		this.gridLayout.setMargin(new MarginInfo(false));
		this.horizontalLayout.setMargin(new MarginInfo(false));
		this.tasksMorning.setSpacing(false);
		this.tasksMorning.setMargin(new MarginInfo(false));
		this.tasksAfternoon.setStyleName("borderLeft");
		this.tasksAfternoon.setSpacing(false);
		this.tasksAfternoon.setMargin(new MarginInfo(false));
		this.tasksDaylong.setSpacing(false);
		this.tasksDaylong.setMargin(new MarginInfo(false));
	
		this.tasksMorning.setWidth(100, Unit.PERCENTAGE);
		this.tasksMorning.setHeight(-1, Unit.PIXELS);
		this.horizontalLayout.addComponent(this.tasksMorning);
		this.horizontalLayout.setComponentAlignment(this.tasksMorning, Alignment.MIDDLE_CENTER);
		this.horizontalLayout.setExpandRatio(this.tasksMorning, 10.0F);
		this.tasksAfternoon.setWidth(100, Unit.PERCENTAGE);
		this.tasksAfternoon.setHeight(-1, Unit.PIXELS);
		this.horizontalLayout.addComponent(this.tasksAfternoon);
		this.horizontalLayout.setComponentAlignment(this.tasksAfternoon, Alignment.MIDDLE_CENTER);
		this.horizontalLayout.setExpandRatio(this.tasksAfternoon, 10.0F);
		this.gridLayout.setColumns(1);
		this.gridLayout.setRows(2);
		this.horizontalLayout.setWidth(100, Unit.PERCENTAGE);
		this.horizontalLayout.setHeight(-1, Unit.PIXELS);
		this.gridLayout.addComponent(this.horizontalLayout, 0, 0);
		this.tasksDaylong.setWidth(100, Unit.PERCENTAGE);
		this.tasksDaylong.setHeight(-1, Unit.PIXELS);
		this.gridLayout.addComponent(this.tasksDaylong, 0, 1);
		this.gridLayout.setColumnExpandRatio(0, 100.0F);
		this.gridLayout.setRowExpandRatio(0, 10.0F);
		this.gridLayout.setWidth(100, Unit.PERCENTAGE);
		this.gridLayout.setHeight(-1, Unit.PIXELS);
		this.addComponent(this.gridLayout);
		this.setExpandRatio(this.gridLayout, 100.0F);
		this.setSizeFull();
	
		this.addContextClickListener(event -> this.this_contextClick(event));
	} // </generated-code>

	// <generated-code name="variables">
	private XdevHorizontalLayout horizontalLayout;
	private XdevGridLayout gridLayout;
	private XdevVerticalLayout tasksAfternoon, tasksMorning, tasksDaylong;
	// </generated-code>


}
