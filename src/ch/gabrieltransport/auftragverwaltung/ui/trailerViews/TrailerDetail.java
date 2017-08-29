package ch.gabrieltransport.auftragverwaltung.ui.trailerViews;

import ch.gabrieltransport.auftragverwaltung.business.refresher.Broadcaster;
import ch.gabrieltransport.auftragverwaltung.dal.AnhaengertypDAO;
import ch.gabrieltransport.auftragverwaltung.dal.FahrzeugFunktionDAO;
import ch.gabrieltransport.auftragverwaltung.entities.Anhaenger;
import ch.gabrieltransport.auftragverwaltung.entities.Anhaenger_;
import ch.gabrieltransport.auftragverwaltung.entities.Anhaengertyp;
import ch.gabrieltransport.auftragverwaltung.entities.Anhaengertyp_;
import ch.gabrieltransport.auftragverwaltung.entities.Fahrzeug;
import ch.gabrieltransport.auftragverwaltung.entities.FahrzeugFunktion;
import ch.gabrieltransport.auftragverwaltung.entities.FahrzeugFunktion_;
import ch.gabrieltransport.auftragverwaltung.entities.Fahrzeug_;
import ch.gabrieltransport.auftragverwaltung.ui.taskDetail.Callback;
import com.vaadin.data.Property;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Window;
import com.xdev.dal.DAOs;
import com.xdev.ui.XdevButton;
import com.xdev.ui.XdevCheckBox;
import com.xdev.ui.XdevFieldGroup;
import com.xdev.ui.XdevGridLayout;
import com.xdev.ui.XdevHorizontalLayout;
import com.xdev.ui.XdevLabel;
import com.xdev.ui.XdevTextField;
import com.xdev.ui.XdevView;
import com.xdev.ui.entitycomponent.combobox.XdevComboBox;

public class TrailerDetail extends XdevView {

	public static class Callback{
		public void onDialogResult(Anhaenger result){}
	}
	private Callback callback;
	/**
	 * 
	 */
	public TrailerDetail(Callback callback) {
		super();
		this.initUI();
		Anhaenger trailer = new Anhaenger();
		this.fieldGroup.setItemDataSource(trailer);
		this.callback = callback;
	}
	
	public TrailerDetail(Anhaenger trailer, Callback callback) {
		super();
		this.initUI();
		this.fieldGroup.setItemDataSource(trailer);
		this.callback = callback;
	}

	/**
	 * Event handler delegate method for the {@link XdevButton} {@link #cmdReset}.
	 *
	 * @see Button.ClickListener#buttonClick(Button.ClickEvent)
	 * @eventHandlerDelegate Do NOT delete, used by UI designer!
	 */
	private void cmdReset_buttonClick(Button.ClickEvent event) {
		((Window)this.getParent()).close();
	}

	/**
	 * Event handler delegate method for the {@link XdevButton} {@link #cmdSave}.
	 *
	 * @see Button.ClickListener#buttonClick(Button.ClickEvent)
	 * @eventHandlerDelegate Do NOT delete, used by UI designer!
	 */
	private void cmdSave_buttonClick(Button.ClickEvent event) {
		callback.onDialogResult(this.fieldGroup.save());
		Broadcaster.broadcast("TRAILER");
		((Window)this.getParent()).close();
	}

	/*
	 * WARNING: Do NOT edit!<br>The content of this method is always regenerated by
	 * the UI designer.
	 */
	// <generated-code name="initUI">
	private void initUI() {
		this.gridLayout = new XdevGridLayout();
		this.form = new XdevGridLayout();
		this.lblNummer = new XdevLabel();
		this.txtNummer = new XdevTextField();
		this.lblKennzeichen = new XdevLabel();
		this.txtKennzeichen = new XdevTextField();
		this.lblNutzlast = new XdevLabel();
		this.txtNutzlast = new XdevTextField();
		this.lblAnhaengertyp = new XdevLabel();
		this.cmbAnhaengertyp = new XdevComboBox<>();
		this.lblFahrzeugFunktion = new XdevLabel();
		this.cmbFahrzeugFunktion = new XdevComboBox<>();
		this.horizontalLayout = new XdevHorizontalLayout();
		this.cmdReset = new XdevButton();
		this.cmdSave = new XdevButton();
		this.fieldGroup = new XdevFieldGroup<>(Anhaenger.class);
	
		this.lblNummer.setValue("Nummer");
		this.txtNummer.setTabIndex(1);
		this.lblKennzeichen.setValue("Kennzeichen");
		this.txtKennzeichen.setTabIndex(2);
		this.lblNutzlast.setValue("Nutzlast");
		this.txtNutzlast.setTabIndex(3);
		this.lblAnhaengertyp.setValue("Anhaengertyp");
		this.cmbAnhaengertyp.setTabIndex(4);
		this.cmbAnhaengertyp.setContainerDataSource(Anhaengertyp.class, DAOs.get(AnhaengertypDAO.class).findAll());
		this.cmbAnhaengertyp.setItemCaptionPropertyId(Anhaengertyp_.beschreibung.getName());
		this.lblFahrzeugFunktion.setValue("FahrzeugFunktion");
		this.cmbFahrzeugFunktion.setTabIndex(5);
		this.cmbFahrzeugFunktion.setContainerDataSource(FahrzeugFunktion.class,
				DAOs.get(FahrzeugFunktionDAO.class).findAll());
		this.cmbFahrzeugFunktion.setItemCaptionPropertyId(FahrzeugFunktion_.beschreibung.getName());
		this.horizontalLayout.setMargin(new MarginInfo(false));
		this.cmdReset.setCaption("Abbrechen");
		this.cmdReset.setTabIndex(6);
		this.cmdSave.setCaption("Speichern");
		this.cmdSave.setTabIndex(7);
		this.fieldGroup.bind(this.txtNummer, Anhaenger_.nummer.getName());
		this.fieldGroup.bind(this.txtKennzeichen, Anhaenger_.kennzeichen.getName());
		this.fieldGroup.bind(this.txtNutzlast, Anhaenger_.nutzlast.getName());
		this.fieldGroup.bind(this.cmbAnhaengertyp, Anhaenger_.anhaengertyp.getName());
		this.fieldGroup.bind(this.cmbFahrzeugFunktion, Anhaenger_.fahrzeugFunktion.getName());
	
		this.cmdReset.setSizeUndefined();
		this.horizontalLayout.addComponent(this.cmdReset);
		this.horizontalLayout.setComponentAlignment(this.cmdReset, Alignment.MIDDLE_LEFT);
		this.cmdSave.setSizeUndefined();
		this.horizontalLayout.addComponent(this.cmdSave);
		this.horizontalLayout.setComponentAlignment(this.cmdSave, Alignment.MIDDLE_LEFT);
		this.form.setColumns(2);
		this.form.setRows(6);
		this.lblNummer.setSizeUndefined();
		this.form.addComponent(this.lblNummer, 0, 0);
		this.txtNummer.setWidth(200, Unit.PIXELS);
		this.txtNummer.setHeight(-1, Unit.PIXELS);
		this.form.addComponent(this.txtNummer, 1, 0);
		this.lblKennzeichen.setSizeUndefined();
		this.form.addComponent(this.lblKennzeichen, 0, 1);
		this.txtKennzeichen.setWidth(200, Unit.PIXELS);
		this.txtKennzeichen.setHeight(-1, Unit.PIXELS);
		this.form.addComponent(this.txtKennzeichen, 1, 1);
		this.lblNutzlast.setSizeUndefined();
		this.form.addComponent(this.lblNutzlast, 0, 2);
		this.txtNutzlast.setWidth(200, Unit.PIXELS);
		this.txtNutzlast.setHeight(-1, Unit.PIXELS);
		this.form.addComponent(this.txtNutzlast, 1, 2);
		this.lblAnhaengertyp.setSizeUndefined();
		this.form.addComponent(this.lblAnhaengertyp, 0, 3);
		this.cmbAnhaengertyp.setWidth(200, Unit.PIXELS);
		this.cmbAnhaengertyp.setHeight(-1, Unit.PIXELS);
		this.form.addComponent(this.cmbAnhaengertyp, 1, 3);
		this.lblFahrzeugFunktion.setSizeUndefined();
		this.form.addComponent(this.lblFahrzeugFunktion, 0, 4);
		this.cmbFahrzeugFunktion.setWidth(200, Unit.PIXELS);
		this.cmbFahrzeugFunktion.setHeight(-1, Unit.PIXELS);
		this.form.addComponent(this.cmbFahrzeugFunktion, 1, 4);
		this.horizontalLayout.setSizeUndefined();
		this.form.addComponent(this.horizontalLayout, 0, 5, 1, 5);
		this.form.setComponentAlignment(this.horizontalLayout, Alignment.TOP_RIGHT);
		this.gridLayout.setColumns(2);
		this.gridLayout.setRows(2);
		this.form.setSizeUndefined();
		this.gridLayout.addComponent(this.form, 0, 0);
		final CustomComponent gridLayout_hSpacer = new CustomComponent();
		gridLayout_hSpacer.setSizeFull();
		this.gridLayout.addComponent(gridLayout_hSpacer, 1, 0, 1, 0);
		this.gridLayout.setColumnExpandRatio(1, 1.0F);
		final CustomComponent gridLayout_vSpacer = new CustomComponent();
		gridLayout_vSpacer.setSizeFull();
		this.gridLayout.addComponent(gridLayout_vSpacer, 0, 1, 0, 1);
		this.gridLayout.setRowExpandRatio(1, 1.0F);
		this.gridLayout.setSizeFull();
		this.setContent(this.gridLayout);
		this.setSizeFull();
	
		this.cmdReset.addClickListener(event -> this.cmdReset_buttonClick(event));
		this.cmdSave.addClickListener(event -> this.cmdSave_buttonClick(event));
	} // </generated-code>

	// <generated-code name="variables">
	private XdevLabel lblNummer, lblKennzeichen, lblNutzlast, lblAnhaengertyp, lblFahrzeugFunktion;
	private XdevButton cmdReset, cmdSave;
	private XdevComboBox<Anhaengertyp> cmbAnhaengertyp;
	private XdevHorizontalLayout horizontalLayout;
	private XdevFieldGroup<Anhaenger> fieldGroup;
	private XdevGridLayout gridLayout, form;
	private XdevTextField txtNummer, txtKennzeichen, txtNutzlast;
	private XdevComboBox<FahrzeugFunktion> cmbFahrzeugFunktion;
	// </generated-code>

}