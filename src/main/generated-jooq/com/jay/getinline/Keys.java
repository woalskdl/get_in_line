/*
 * This file is generated by jOOQ.
 */
package com.jay.getinline;


import com.jay.getinline.tables.Admin;
import com.jay.getinline.tables.AdminPlaceMap;
import com.jay.getinline.tables.Event;
import com.jay.getinline.tables.Place;
import com.jay.getinline.tables.records.AdminPlaceMapRecord;
import com.jay.getinline.tables.records.AdminRecord;
import com.jay.getinline.tables.records.EventRecord;
import com.jay.getinline.tables.records.PlaceRecord;

import org.jooq.ForeignKey;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.Internal;


/**
 * A class modelling foreign key relationships and constraints of tables in 
 * getinline.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<AdminRecord> KEY_ADMIN_PRIMARY = Internal.createUniqueKey(Admin.ADMIN, DSL.name("KEY_admin_PRIMARY"), new TableField[] { Admin.ADMIN.ID }, true);
    public static final UniqueKey<AdminRecord> KEY_ADMIN_UK_C0R9ATAMXVBHJJVY5J8DA1KAM = Internal.createUniqueKey(Admin.ADMIN, DSL.name("KEY_admin_UK_c0r9atamxvbhjjvy5j8da1kam"), new TableField[] { Admin.ADMIN.EMAIL }, true);
    public static final UniqueKey<AdminRecord> KEY_ADMIN_UK_RR9FV9WXQKIJJVFBTUUYLIH7W = Internal.createUniqueKey(Admin.ADMIN, DSL.name("KEY_admin_UK_rr9fv9wxqkijjvfbtuuylih7w"), new TableField[] { Admin.ADMIN.NICKNAME }, true);
    public static final UniqueKey<AdminPlaceMapRecord> KEY_ADMIN_PLACE_MAP_PRIMARY = Internal.createUniqueKey(AdminPlaceMap.ADMIN_PLACE_MAP, DSL.name("KEY_admin_place_map_PRIMARY"), new TableField[] { AdminPlaceMap.ADMIN_PLACE_MAP.ID }, true);
    public static final UniqueKey<EventRecord> KEY_EVENT_PRIMARY = Internal.createUniqueKey(Event.EVENT, DSL.name("KEY_event_PRIMARY"), new TableField[] { Event.EVENT.ID }, true);
    public static final UniqueKey<PlaceRecord> KEY_PLACE_PRIMARY = Internal.createUniqueKey(Place.PLACE, DSL.name("KEY_place_PRIMARY"), new TableField[] { Place.PLACE.ID }, true);

    // -------------------------------------------------------------------------
    // FOREIGN KEY definitions
    // -------------------------------------------------------------------------

    public static final ForeignKey<AdminPlaceMapRecord, PlaceRecord> FK14BA9O1UKLVJWVLO445WY96W3 = Internal.createForeignKey(AdminPlaceMap.ADMIN_PLACE_MAP, DSL.name("FK14ba9o1uklvjwvlo445wy96w3"), new TableField[] { AdminPlaceMap.ADMIN_PLACE_MAP.PLACE_ID }, Keys.KEY_PLACE_PRIMARY, new TableField[] { Place.PLACE.ID }, true);
    public static final ForeignKey<AdminPlaceMapRecord, AdminRecord> FKT136HWBT0JE03N7N0SDM6MTPK = Internal.createForeignKey(AdminPlaceMap.ADMIN_PLACE_MAP, DSL.name("FKt136hwbt0je03n7n0sdm6mtpk"), new TableField[] { AdminPlaceMap.ADMIN_PLACE_MAP.ADMIN_ID }, Keys.KEY_ADMIN_PRIMARY, new TableField[] { Admin.ADMIN.ID }, true);
    public static final ForeignKey<EventRecord, PlaceRecord> FKPUVIX4LEXRAKGDLT8SI1TBTXV = Internal.createForeignKey(Event.EVENT, DSL.name("FKpuvix4lexrakgdlt8si1tbtxv"), new TableField[] { Event.EVENT.PLACE_ID }, Keys.KEY_PLACE_PRIMARY, new TableField[] { Place.PLACE.ID }, true);
}
