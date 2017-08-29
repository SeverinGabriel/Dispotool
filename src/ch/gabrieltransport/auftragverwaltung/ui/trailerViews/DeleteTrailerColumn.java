
package ch.gabrieltransport.auftragverwaltung.ui.trailerViews;

import ch.gabrieltransport.auftragverwaltung.business.facade.FahrzeugServiceFacade;
import ch.gabrieltransport.auftragverwaltung.business.facade.TrailerServiceFacade;
import ch.gabrieltransport.auftragverwaltung.business.refresher.Broadcaster;
import ch.gabrieltransport.auftragverwaltung.dal.FahrzeugDAO;
import ch.gabrieltransport.auftragverwaltung.entities.Anhaenger;
import ch.gabrieltransport.auftragverwaltung.entities.Fahrzeug;
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
import com.vaadin.ui.Table.ColumnGenerator;
import com.xdev.ui.XdevButton;
import com.xdev.ui.XdevHorizontalLayout;
import com.xdev.ui.entitycomponent.table.XdevTable;

public class DeleteTrailerColumn extends XdevHorizontalLayout {

	public static class Generator implements ColumnGenerator {
		@Override
		public Object generateCell(Table table, Object itemId, Object columnId) {

			return new DeleteTrailerColumn(table, itemId, columnId);
		}
	}

	private final Table customizedTable;
	private final Object itemId;
	private final Object columnId;
	private TrailerServiceFacade trailerFacade = new TrailerServiceFacade();

	private DeleteTrailerColumn(Table customizedTable, Object itemId, Object columnId) {
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
	public Anhaenger getBean() {
		return ((XdevTable<Anhaenger>) getTable()).getBeanItem(getItemId()).getBean();
	}

	/**
	 * Event handler delegate method for the {@link XdevButton} {@link #button}.
	 *
	 * @see ClickListener#buttonClick(ClickEvent)
	 * @eventHandlerDelegate
	 */
	private void button_buttonClick(ClickEvent event) {
		selectItem();
		trailerFacade.deleteTrailer(getBean());
		getTable().getContainerDataSource().removeItem(getBean());
		Broadcaster.broadcast("TRAILER");
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
		this.button.setIcon(FontAwesome.REMOVE);
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
