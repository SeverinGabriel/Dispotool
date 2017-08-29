
package ch.gabrieltransport.auftragverwaltung.ui.fahrerViews;

import ch.gabrieltransport.auftragverwaltung.entities.Fahrer;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.Window;
import com.vaadin.ui.Table.ColumnGenerator;
import com.xdev.ui.XdevButton;
import com.xdev.ui.XdevHorizontalLayout;
import com.xdev.ui.entitycomponent.table.XdevTable;

public class EditEmployeeColumn extends XdevHorizontalLayout {

	public static class Generator implements ColumnGenerator {
		@Override
		public Object generateCell(Table table, Object itemId, Object columnId) {

			return new EditEmployeeColumn(table, itemId, columnId);
		}
	}

	private final Table customizedTable;
	private final Object itemId;
	private final Object columnId;

	private EditEmployeeColumn(Table customizedTable, Object itemId, Object columnId) {
		super();

		this.customizedTable = customizedTable;
		this.itemId = itemId;
		this.columnId = columnId;

		this.initUI();
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
	public Fahrer getBean() {
		return ((XdevTable<Fahrer>) getTable()).getBeanItem(getItemId()).getBean();
	}

	/**
	 * Event handler delegate method for the {@link XdevButton} {@link #button}.
	 *
	 * @see ClickListener#buttonClick(ClickEvent)
	 * @eventHandlerDelegate
	 */
	private void button_buttonClick(ClickEvent event) {
		selectItem();
		Window win = new Window();
		win.setWidth("600");
		win.setHeight("600");
		win.center();
		win.setModal(true);
		DriverDetail employeeView = new DriverDetail(getBean() ,new DriverDetail.Callback() {
		      public void onDialogResult(Fahrer result) {
		    	  getTable().refreshRowCache();
		      }
		});
		win.setContent(employeeView);
		getUI().addWindow(win);
	}

	/**
	 * Event handler delegate method for the {@link XdevHorizontalLayout}.
	 *
	 * @see LayoutClickListener#layoutClick(LayoutClickEvent)
	 * @eventHandlerDelegate
	 */
	private void this_layoutClick(LayoutClickEvent event) {
		selectItem();
	}

	private void selectItem() {
		getTable().select(getItemId());
	}

	/*
	 * WARNING: Do NOT edit!<br>The content of this method is always regenerated by
	 * the UI designer.
	 */
	// <generated-code name="initUI">
	private void initUI() {
		this.button = new XdevButton();
	
		this.setSpacing(false);
		this.setMargin(new MarginInfo(false));
		this.button.setIcon(FontAwesome.PENCIL);
		this.button.setCaption("");
	
		this.button.setSizeUndefined();
		this.addComponent(this.button);
		this.setComponentAlignment(this.button, Alignment.MIDDLE_CENTER);
		final CustomComponent this_spacer = new CustomComponent();
		this_spacer.setSizeFull();
		this.addComponent(this_spacer);
		this.setExpandRatio(this_spacer, 1.0F);
		this.setSizeFull();
	
		this.addLayoutClickListener(event -> this.this_layoutClick(event));
		this.button.addClickListener(event -> this.button_buttonClick(event));
	} // </generated-code>

	// <generated-code name="variables">
	private XdevButton button; // </generated-code>

}