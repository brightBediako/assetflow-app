package com.assetflow.mobile.core.data.mock

import com.assetflow.mobile.core.domain.model.Asset
import com.assetflow.mobile.core.domain.model.OperationalStatus

object MockAssets {
    val categories = listOf(
        "Electronics",
        "Media",
        "Furniture",
        "Laboratory",
        "Vehicles",
        "Events",
        "Tools",
    )

    val assets = listOf(
        Asset(
            id = "asset-001",
            name = "Dell Latitude 5540",
            category = "Electronics",
            location = "IT Store Room A",
            tagNumber = "NB-EL-1001",
            status = OperationalStatus.Available,
            description = "14-inch business laptop with docking station and charger.",
            lastMaintenanceHint = "Serviced 2 months ago",
        ),
        Asset(
            id = "asset-002",
            name = "MacBook Pro 16\"",
            category = "Electronics",
            location = "IT Store Room A",
            tagNumber = "NB-EL-1002",
            status = OperationalStatus.Booked,
            description = "Creative production laptop reserved for media projects.",
            activeBookingId = "booking-002",
        ),
        Asset(
            id = "asset-003",
            name = "Canon EOS R6 Camera",
            category = "Media",
            location = "Media Lab",
            tagNumber = "NB-MD-2001",
            status = OperationalStatus.Available,
            description = "Mirrorless camera kit with 24-105mm lens and spare battery.",
            lastMaintenanceHint = "Lens cleaned last week",
        ),
        Asset(
            id = "asset-004",
            name = "Epson PowerLite Projector",
            category = "Media",
            location = "Conference Room Store",
            tagNumber = "NB-MD-2002",
            status = OperationalStatus.MaintenanceDue,
            description = "4000-lumen projector for lectures and events.",
            lastMaintenanceHint = "Lamp hours nearing replacement",
            activeBookingId = "booking-006",
        ),
        Asset(
            id = "asset-005",
            name = "Bosch Power Drill Set",
            category = "Tools",
            location = "Workshop",
            tagNumber = "NB-TL-3001",
            status = OperationalStatus.Available,
            description = "Cordless drill set with two batteries and carrying case.",
        ),
        Asset(
            id = "asset-006",
            name = "Toyota Hilux Fleet Van",
            category = "Vehicles",
            location = "Parking Bay 3",
            tagNumber = "NB-VH-4001",
            status = OperationalStatus.Booked,
            description = "Campus fleet van for departmental deliveries and field trips.",
            activeBookingId = "booking-003",
        ),
        Asset(
            id = "asset-007",
            name = "Zeiss Microscope",
            category = "Laboratory",
            location = "Science Lab 2",
            tagNumber = "NB-LB-5001",
            status = OperationalStatus.Unavailable,
            description = "Precision microscope undergoing calibration after relocation.",
            lastMaintenanceHint = "Calibration in progress",
        ),
        Asset(
            id = "asset-008",
            name = "Shure SM58 Microphone Pack",
            category = "Events",
            location = "Events Storage",
            tagNumber = "NB-EV-6001",
            status = OperationalStatus.Available,
            description = "Set of four wired microphones with stands and cables.",
        ),
        Asset(
            id = "asset-009",
            name = "HP LaserJet Pro Printer",
            category = "Electronics",
            location = "Admin Office",
            tagNumber = "NB-EL-1003",
            status = OperationalStatus.Available,
            description = "Departmental printer with duplex and network scanning.",
        ),
        Asset(
            id = "asset-010",
            name = "Nikon Z6 Camera",
            category = "Media",
            location = "Media Lab",
            tagNumber = "NB-MD-2003",
            status = OperationalStatus.Booked,
            description = "Full-frame camera for campus photography assignments.",
            activeBookingId = "booking-004",
        ),
        Asset(
            id = "asset-011",
            name = "Steel Folding Chairs (Set of 20)",
            category = "Furniture",
            location = "Events Storage",
            tagNumber = "NB-FR-7001",
            status = OperationalStatus.Available,
            description = "Stackable chairs for seminars, fairs, and outdoor events.",
        ),
        Asset(
            id = "asset-012",
            name = "DeWalt Circular Saw",
            category = "Tools",
            location = "Workshop",
            tagNumber = "NB-TL-3002",
            status = OperationalStatus.MaintenanceDue,
            description = "Heavy-duty circular saw used by facilities maintenance.",
            lastMaintenanceHint = "Blade guard inspection overdue",
        ),
        Asset(
            id = "asset-013",
            name = "iPad Pro 12.9\"",
            category = "Electronics",
            location = "Library Desk 4",
            tagNumber = "NB-EL-1004",
            status = OperationalStatus.Available,
            description = "Tablet for digital sign-in, surveys, and mobile presentations.",
        ),
        Asset(
            id = "asset-014",
            name = "PA Speaker System",
            category = "Events",
            location = "Events Storage",
            tagNumber = "NB-EV-6002",
            status = OperationalStatus.Unavailable,
            description = "Portable PA system temporarily out of service after amplifier repair.",
            lastMaintenanceHint = "Awaiting replacement amplifier",
        ),
    )

    fun getAssetById(assetId: String): Asset? = assets.firstOrNull { it.id == assetId }

    fun getAssetsByCategory(category: String): List<Asset> =
        if (category == "All") assets else assets.filter { it.category == category }

    fun getAssetsByStatus(status: OperationalStatus): List<Asset> =
        assets.filter { it.status == status }
}
