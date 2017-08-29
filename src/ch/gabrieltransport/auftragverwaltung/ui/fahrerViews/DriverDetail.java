package ch.gabrieltransport.auftragverwaltung.ui.fahrerViews;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.vaadin.addons.comboboxmultiselect.ComboBoxMultiselect;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.FieldEvents;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Window;
import com.xdev.ui.XdevButton;
import com.xdev.ui.XdevFieldGroup;
import com.xdev.ui.XdevGridLayout;
import com.xdev.ui.XdevHorizontalLayout;
import com.xdev.ui.XdevLabel;
import com.xdev.ui.XdevTextField;
import com.xdev.ui.XdevView;

import ch.gabrieltransport.auftragverwaltung.business.refresher.Broadcaster;
import ch.gabrieltransport.auftragverwaltung.dal.FahrerDAO;
import ch.gabrieltransport.auftragverwaltung.dal.FahrerfunktionDAO;
import ch.gabrieltransport.auftragverwaltung.dal.FahrerfunktionmapDAO;
import ch.gabrieltransport.auftragverwaltung.entities.Fahrer;
import ch.gabrieltransport.auftragverwaltung.entities.Fahrerfunktion;
import ch.gabrieltransport.auftragverwaltung.entities.Fahrerfunktionmap;

public class DriverDetail extends XdevView {

	final ComboBoxMultiselect comboBoxMultiselect = new ComboBoxMultiselect();
	FahrerDAO fahrerDAO = new FahrerDAO();
	FahrerfunktionmapDAO fahrerFunktionMapDAO = new FahrerfunktionmapDAO();
	FahrerfunktionDAO fahrerFunktionDAO = new FahrerfunktionDAO();
	Fahrer currentEmployee;
	BeanItemContainer<Fahrerfunktion> funktionenBeanContainer;
	List<Fahrerfunktion> funktionen;
	/**
	 * 
	 */
	public DriverDetail(Callback callback) {
		super();
		this.initUI();
		this.callback = callback;
		currentEmployee = new Fahrer();
		this.fieldGroup.setItemDataSource(currentEmployee);
		setupFunctionsDropdown();
	}
	
	public DriverDetail(Fahrer driver, Callback callback) {
		super();
		this.initUI();
		this.callback = callback;
		currentEmployee = driver;
		this.fieldGroup.setItemDataSource(currentEmployee);
		setupFunctionsDropdown();
		selectFunctionsForDriver(driver);
	}
	
	public void setupFunctionsDropdown(){
		FahrerfunktionDAO fahrerFunktion = new FahrerfunktionDAO();
		funktionen = fahrerFunktion.findAll();
		funktionenBeanContainer = new BeanItemContainer<Fahrerfunktion>(Fahrerfunktion.class);
		funktionenBeanContainer.addAll(funktionen);
		
		// Initialize the ComboBoxMultiselect
		comboBoxMultiselect.setClearButtonCaption("Auswahl löschen");
		comboBoxMultiselect.setSelectAllButtonCaption("Alle");
        comboBoxMultiselect.setContainerDataSource(funktionenBeanContainer);
        comboBoxMultiselect.setItemCaptionPropertyId("beschreibung");
        comboBoxMultiselect.unselectAll();
        comboBoxMultiselect.setSelectedStaticCaption("Funktionen", "Funktionen");  
		horizontalLayout2.addComponent(comboBoxMultiselect);
	}
	
	public void selectFunctionsForDriver(Fahrer driver){
		driver = fahrerDAO.merge(driver);
		fahrerDAO.refresh(driver);
		
		List<Fahrerfunktion> driverFunctions = new ArrayList<Fahrerfunktion>(); 
		driver.getFahrerfunktionmaps().forEach(m -> driverFunctions.add(m.getFahrerfunktion()));
		for (Fahrerfunktion ffDropdown : funktionen) {
			for(Fahrerfunktion ffDriver: driverFunctions)
			if(ffDropdown.getBeschreibung().equalsIgnoreCase(ffDriver.getBeschreibung()) ){
				comboBoxMultiselect.select(ffDropdown);
			}
		}
	}
	
	public static class Callback{
		public void onDialogResult(Fahrer result){}
	}
	private Callback callback;

	/**
	 * Event handler delegate method for the {@link XdevButton} {@link #cmdReturn}.
	 *
	 * @see Button.ClickListener#buttonClick(Button.ClickEvent)
	 * @eventHandlerDelegate Do NOT delete, used by UI designer!
	 */
	private void cmdReturn_buttonClick(Button.ClickEvent event) {
		((Window)this.getParent()).close();
	}

	/**
	 * Event handler delegate method for the {@link XdevButton} {@link #cmdSave}.
	 *
	 * @see Button.ClickListener#buttonClick(Button.ClickEvent)
	 * @eventHandlerDelegate Do NOT delete, used by UI designer!
	 */
	private void cmdSave_buttonClick(Button.ClickEvent event) {
		String vorname = txtVorname.getValue();
		String nachname = txtNachname.getValue();
		txtName.setValue(nachname + ", " + vorname);
		Fahrer currentEmployee = this.fieldGroup.save();
		Set<Fahrerfunktion> funktionen = (Set<Fahrerfunktion>) comboBoxMultiselect.getValue();
		saveFunctions(funktionen);
		callback.onDialogResult(currentEmployee);
		Broadcaster.broadcast("DRIVER");
		((Window)this.getParent()).close();
	}

	@Transactional
	private void saveFunctions(Set<Fahrerfunktion> funktionen){
		
		currentEmployee = fahrerDAO.merge(currentEmployee);
		fahrerFunktionMapDAO.deleteByFahrer(currentEmployee);
		Set<Fahrerfunktionmap> ffm = new HashSet<Fahrerfunktionmap>();
		for(Fahrerfunktion ff : funktionen){
			Fahrerfunktionmap map = new Fahrerfunktionmap();
			map.setFahrer(currentEmployee);
			map.setFahrerfunktion(fahrerFunktionDAO.merge(ff));
			fahrerFunktionMapDAO.persist(map);
			ffm.add(map);
		}
		currentEmployee.setFahrerfunktionmaps(ffm);
		fahrerDAO.merge(currentEmployee);
		
	}
	
	/**
	 * Event handler delegate method for the {@link XdevTextField}
	 * {@link #txtNachname}.
	 *
	 * @see FieldEvents.TextChangeListener#textChange(FieldEvents.TextChangeEvent)
	 * @eventHandlerDelegate Do NOT delete, used by UI designer!
	 */
	private void txtNachname_textChange(FieldEvents.TextChangeEvent event) {
		txtName.setValue(txtNachname.getValue() + ", " + txtVorname.getValue());
	}

	/**
	 * Event handler delegate method for the {@link XdevTextField}
	 * {@link #txtVorname}.
	 *
	 * @see FieldEvents.TextChangeListener#textChange(FieldEvents.TextChangeEvent)
	 * @eventHandlerDelegate Do NOT delete, used by UI designer!
	 */
	private void txtVorname_textChange(FieldEvents.TextChangeEvent event) {
		txtName.setValue(txtNachname.getValue() + ", " + txtVorname.getValue());
	}

	/*
	 * WARNING: Do NOT edit!<br>The content of this method is always regenerated
	 * by the UI designer.
	 */
	// <generated-code name="initUI">
	private void initUI() {
		this.gridLayout = new XdevGridLayout();
		this.form = new XdevGridLayout();
		this.label = new XdevLabel();
		this.horizontalLayout2 = new XdevHorizontalLayout();
		this.lblNachname = new XdevLabel();
		this.txtNachname = new XdevTextField();
		this.lblVorname = new XdevLabel();
		this.txtVorname = new XdevTextField();
		this.lblName = new XdevLabel();
		this.txtName = new XdevTextField();
		this.lblTelefon = new XdevLabel();
		this.txtTelefon = new XdevTextField();
		this.horizontalLayout = new XdevHorizontalLayout();
		this.cmdReturn = new XdevButton();
		this.cmdSave = new XdevButton();
		this.fieldGroup = new XdevFieldGroup<>(Fahrer.class);
	
		this.label.setValue("Funktionen");
		this.horizontalLayout2.setSpacing(false);
		this.horizontalLayout2.setMargin(new MarginInfo(false));
		this.lblNachname.setValue("Nachname");
		this.txtNachname.setTabIndex(1);
		this.lblVorname.setValue("Vorname");
		this.txtVorname.setTabIndex(2);
		this.lblName.setValue("Name, Vorname");
		this.txtName.setEnabled(false);
		this.txtName.setReadOnly(true);
		this.lblTelefon.setValue("Telefon");
		this.txtTelefon.setTabIndex(4);
		this.horizontalLayout.setMargin(new MarginInfo(false));
		this.cmdReturn.setCaption("Abbrechen");
		this.cmdReturn.setTabIndex(6);
		this.cmdSave.setCaption("Speichern");
		this.cmdSave.setTabIndex(7);
		this.fieldGroup.bind(this.txtNachname, "nachname");
		this.fieldGroup.bind(this.txtVorname, "vorname");
		this.fieldGroup.bind(this.txtName, "name");
		this.fieldGroup.bind(this.txtTelefon, "telefon");
	
		this.cmdReturn.setSizeUndefined();
		this.horizontalLayout.addComponent(this.cmdReturn);
		this.horizontalLayout.setComponentAlignment(this.cmdReturn, Alignment.MIDDLE_LEFT);
		this.cmdSave.setSizeUndefined();
		this.horizontalLayout.addComponent(this.cmdSave);
		this.horizontalLayout.setComponentAlignment(this.cmdSave, Alignment.MIDDLE_LEFT);
		CustomComponent horizontalLayout_spacer = new CustomComponent();
		horizontalLayout_spacer.setSizeFull();
		this.horizontalLayout.addComponent(horizontalLayout_spacer);
		this.horizontalLayout.setExpandRatio(horizontalLayout_spacer, 1.0F);
		this.form.setColumns(3);
		this.form.setRows(7);
		this.label.setSizeUndefined();
		this.form.addComponent(this.label, 0, 4);
		this.horizontalLayout2.setWidth(200, Unit.PIXELS);
		this.horizontalLayout2.setHeight(-1, Unit.PIXELS);
		this.form.addComponent(this.horizontalLayout2, 1, 4);
		this.lblNachname.setSizeUndefined();
		this.form.addComponent(this.lblNachname, 0, 0);
		this.txtNachname.setWidth(200, Unit.PIXELS);
		this.txtNachname.setHeight(-1, Unit.PIXELS);
		this.form.addComponent(this.txtNachname, 1, 0);
		this.lblVorname.setSizeUndefined();
		this.form.addComponent(this.lblVorname, 0, 1);
		this.txtVorname.setWidth(200, Unit.PIXELS);
		this.txtVorname.setHeight(-1, Unit.PIXELS);
		this.form.addComponent(this.txtVorname, 1, 1);
		this.lblName.setSizeUndefined();
		this.form.addComponent(this.lblName, 0, 2);
		this.txtName.setWidth(200, Unit.PIXELS);
		this.txtName.setHeight(-1, Unit.PIXELS);
		this.form.addComponent(this.txtName, 1, 2);
		this.lblTelefon.setSizeUndefined();
		this.form.addComponent(this.lblTelefon, 0, 3);
		this.txtTelefon.setWidth(200, Unit.PIXELS);
		this.txtTelefon.setHeight(-1, Unit.PIXELS);
		this.form.addComponent(this.txtTelefon, 1, 3);
		this.horizontalLayout.setSizeUndefined();
		this.form.addComponent(this.horizontalLayout, 0, 5, 1, 5);
		this.form.setComponentAlignment(this.horizontalLayout, Alignment.TOP_RIGHT);
		CustomComponent form_hSpacer = new CustomComponent();
		form_hSpacer.setSizeFull();
		this.form.addComponent(form_hSpacer, 2, 0, 2, 5);
		this.form.setColumnExpandRatio(2, 1.0F);
		CustomComponent form_vSpacer = new CustomComponent();
		form_vSpacer.setSizeFull();
		this.form.addComponent(form_vSpacer, 0, 6, 1, 6);
		this.form.setRowExpandRatio(6, 1.0F);
		this.gridLayout.setColumns(1);
		this.gridLayout.setRows(2);
		this.form.setWidth(100, Unit.PERCENTAGE);
		this.form.setHeight(-1, Unit.PIXELS);
		this.gridLayout.addComponent(this.form, 0, 0);
		this.gridLayout.setColumnExpandRatio(0, 0.4F);
		CustomComponent gridLayout_vSpacer = new CustomComponent();
		gridLayout_vSpacer.setSizeFull();
		this.gridLayout.addComponent(gridLayout_vSpacer, 0, 1, 0, 1);
		this.gridLayout.setRowExpandRatio(1, 1.0F);
		this.gridLayout.setSizeFull();
		this.setContent(this.gridLayout);
		this.setSizeFull();
	
		txtNachname.addTextChangeListener(event -> this.txtNachname_textChange(event));
		txtVorname.addTextChangeListener(event -> this.txtVorname_textChange(event));
		cmdReturn.addClickListener(event -> this.cmdReturn_buttonClick(event));
		cmdSave.addClickListener(event -> this.cmdSave_buttonClick(event));
	} // </generated-code>

	// <generated-code name="variables">
	private XdevLabel label, lblNachname, lblVorname, lblName, lblTelefon;
	private XdevButton cmdReturn, cmdSave;
	private XdevHorizontalLayout horizontalLayout2, horizontalLayout;
	private XdevGridLayout gridLayout, form;
	private XdevTextField txtNachname, txtVorname, txtName, txtTelefon;
	private XdevFieldGroup<Fahrer> fieldGroup;
	// </generated-code>

}
