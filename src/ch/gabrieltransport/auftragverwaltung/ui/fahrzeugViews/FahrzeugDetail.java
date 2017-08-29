package ch.gabrieltransport.auftragverwaltung.ui.fahrzeugViews;

import ch.gabrieltransport.auftragverwaltung.business.refresher.Broadcaster;
import ch.gabrieltransport.auftragverwaltung.dal.AnhaengertypDAO;
import ch.gabrieltransport.auftragverwaltung.dal.FahrzeugFunktionDAO;
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

public class FahrzeugDetail extends XdevView {

	public static class Callback{
		public void onDialogResult(Fahrzeug result){}
	}
	private Callback callback;
	/**
	 * 
	 */
	public FahrzeugDetail(Callback callback) {
		super();
		this.initUI();
		Fahrzeug vehicle = new Fahrzeug();
		vehicle.setAnhaenger(true);
		this.fieldGroup.setItemDataSource(vehicle);
		this.callback = callback;
	}
	
	public FahrzeugDetail(Fahrzeug vehicle, Callback callback) {
		super();
		this.initUI();
		this.fieldGroup.setItemDataSource(vehicle);
		this.callback = callback;
	}

	/**
	 * Event handler delegate method for the {@link XdevButton} {@link #cmdBack}.
	 *
	 * @see Button.ClickListener#buttonClick(Button.ClickEvent)
	 * @eventHandlerDelegate Do NOT delete, used by UI designer!
	 */
	private void cmdBack_buttonClick(Button.ClickEvent event) {
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
		Broadcaster.broadcast("VEHICLE");
		((Window)this.getParent()).close();
	}

	/**
	 * Event handler delegate method for the {@link XdevCheckBox}
	 * {@link #chkAnhaenger}.
	 *
	 * @see Property.ValueChangeListener#valueChange(Property.ValueChangeEvent)
	 * @eventHandlerDelegate Do NOT delete, used by UI designer!
	 */
	private void chkAnhaenger_valueChange(Property.ValueChangeEvent event) {
		lblAnhaengertyp.setVisible(chkAnhaenger.getValue());
		cmbAnhaengertyp.setVisible(chkAnhaenger.getValue());
		if (!chkAnhaenger.getValue()){
			cmbAnhaengertyp.select(null);
		}
			
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
		this.lblFahrzeugFunktion = new XdevLabel();
		this.cmbFahrzeugFunktion = new XdevComboBox<>();
		this.lblAnhaenger = new XdevLabel();
		this.chkAnhaenger = new XdevCheckBox();
		this.lblAnhaengertyp = new XdevLabel();
		this.cmbAnhaengertyp = new XdevComboBox<>();
		this.horizontalLayout = new XdevHorizontalLayout();
		this.cmdBack = new XdevButton();
		this.cmdSave = new XdevButton();
		this.fieldGroup = new XdevFieldGroup<>(Fahrzeug.class);
	
		this.lblNummer.setValue("Nummer");
		this.txtNummer.setColumns(5);
		this.txtNummer.setTabIndex(1);
		this.lblKennzeichen.setValue("Kennzeichen");
		this.txtKennzeichen.setColumns(5);
		this.txtKennzeichen.setTabIndex(2);
		this.lblNutzlast.setValue("Nutzlast");
		this.txtNutzlast.setColumns(5);
		this.txtNutzlast.setTabIndex(3);
		this.lblFahrzeugFunktion.setValue("FahrzeugFunktion");
		this.cmbFahrzeugFunktion.setTabIndex(4);
		this.cmbFahrzeugFunktion.setContainerDataSource(FahrzeugFunktion.class,
				DAOs.get(FahrzeugFunktionDAO.class).findAll());
		this.cmbFahrzeugFunktion.setItemCaptionPropertyId(FahrzeugFunktion_.beschreibung.getName());
		this.lblAnhaenger.setValue("Anhaenger");
		this.chkAnhaenger.setCaption("");
		this.chkAnhaenger.setTabIndex(6);
		this.chkAnhaenger.setImmediate(true);
		this.lblAnhaengertyp.setValue("Anhaengertyp");
		this.cmbAnhaengertyp.setTabIndex(5);
		this.cmbAnhaengertyp.setContainerDataSource(Anhaengertyp.class, DAOs.get(AnhaengertypDAO.class).findAll());
		this.cmbAnhaengertyp.setItemCaptionPropertyId(Anhaengertyp_.beschreibung.getName());
		this.horizontalLayout.setMargin(new MarginInfo(false));
		this.cmdBack.setCaption("Abbrechen");
		this.cmdBack.setTabIndex(8);
		this.cmdSave.setCaption("Speichern");
		this.cmdSave.setTabIndex(9);
		this.fieldGroup.bind(this.txtNummer, Fahrzeug_.nummer.getName());
		this.fieldGroup.bind(this.txtKennzeichen, Fahrzeug_.kennzeichen.getName());
		this.fieldGroup.bind(this.txtNutzlast, Fahrzeug_.nutzlast.getName());
		this.fieldGroup.bind(this.cmbFahrzeugFunktion, Fahrzeug_.fahrzeugFunktion.getName());
		this.fieldGroup.bind(this.cmbAnhaengertyp, Fahrzeug_.anhaengertyp.getName());
		this.fieldGroup.bind(this.chkAnhaenger, Fahrzeug_.anhaenger.getName());
	
		this.cmdBack.setSizeUndefined();
		this.horizontalLayout.addComponent(this.cmdBack);
		this.horizontalLayout.setComponentAlignment(this.cmdBack, Alignment.MIDDLE_LEFT);
		this.cmdSave.setSizeUndefined();
		this.horizontalLayout.addComponent(this.cmdSave);
		this.horizontalLayout.setComponentAlignment(this.cmdSave, Alignment.MIDDLE_LEFT);
		this.form.setColumns(2);
		this.form.setRows(7);
		this.lblNummer.setSizeUndefined();
		this.form.addComponent(this.lblNummer, 0, 0);
		this.txtNummer.setSizeUndefined();
		this.form.addComponent(this.txtNummer, 1, 0);
		this.lblKennzeichen.setSizeUndefined();
		this.form.addComponent(this.lblKennzeichen, 0, 1);
		this.txtKennzeichen.setSizeUndefined();
		this.form.addComponent(this.txtKennzeichen, 1, 1);
		this.lblNutzlast.setSizeUndefined();
		this.form.addComponent(this.lblNutzlast, 0, 2);
		this.txtNutzlast.setSizeUndefined();
		this.form.addComponent(this.txtNutzlast, 1, 2);
		this.lblFahrzeugFunktion.setSizeUndefined();
		this.form.addComponent(this.lblFahrzeugFunktion, 0, 3);
		this.cmbFahrzeugFunktion.setSizeUndefined();
		this.form.addComponent(this.cmbFahrzeugFunktion, 1, 3);
		this.lblAnhaenger.setSizeUndefined();
		this.form.addComponent(this.lblAnhaenger, 0, 4);
		this.chkAnhaenger.setSizeUndefined();
		this.form.addComponent(this.chkAnhaenger, 1, 4);
		this.lblAnhaengertyp.setSizeUndefined();
		this.form.addComponent(this.lblAnhaengertyp, 0, 5);
		this.cmbAnhaengertyp.setSizeUndefined();
		this.form.addComponent(this.cmbAnhaengertyp, 1, 5);
		this.horizontalLayout.setSizeUndefined();
		this.form.addComponent(this.horizontalLayout, 0, 6);
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
	
		this.chkAnhaenger.addValueChangeListener(new Property.ValueChangeListener() {
			@Override
			public void valueChange(Property.ValueChangeEvent event) {
				FahrzeugDetail.this.chkAnhaenger_valueChange(event);
			}
		});
		this.cmdBack.addClickListener(event -> this.cmdBack_buttonClick(event));
		this.cmdSave.addClickListener(event -> this.cmdSave_buttonClick(event));
	} // </generated-code>

	// <generated-code name="variables">
	private XdevLabel lblNummer, lblKennzeichen, lblNutzlast, lblFahrzeugFunktion, lblAnhaengertyp, lblAnhaenger;
	private XdevButton cmdBack, cmdSave;
	private XdevComboBox<Anhaengertyp> cmbAnhaengertyp;
	private XdevHorizontalLayout horizontalLayout;
	private XdevCheckBox chkAnhaenger;
	private XdevGridLayout gridLayout, form;
	private XdevTextField txtNummer, txtKennzeichen, txtNutzlast;
	private XdevFieldGroup<Fahrzeug> fieldGroup;
	private XdevComboBox<FahrzeugFunktion> cmbFahrzeugFunktion;
	// </generated-code>

}