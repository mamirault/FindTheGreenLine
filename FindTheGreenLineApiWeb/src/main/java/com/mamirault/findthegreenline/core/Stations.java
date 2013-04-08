package com.mamirault.findthegreenline.core;

public enum Stations {
  
  //Trunk
  LECHMERE("Lechmere Station, Cambridge, MA 02141, USA", "Lechmere", 42.37077, -71.07654000000001),
  SCIENCE_PARK("Science Park Station, Boston, MA 02114, USA", "Science Park", 42.36666, -71.06767),
  NORTH("North Station, Boston, MA 02114, USA", "North", 42.36558,-71.06129),
  HAYMARKET("Haymarket Station, Boston, MA 02114, USA", "Haymarket", 42.36302000000001, -71.05829),
  GOVERNMENT_CENTER("Government Center Station, Boston, MA 02203, USA", "Government Center", 42.3597, -71.05921000000001),
  PARK_ST("Park St. Station, Boston, MA 02108, USA", "Park St.", 42.35639, -71.06242),
  BOYLSTON("Boylston Station, Boston, MA 02108, USA", "Boylston", 42.35302, -71.06459000000001),
  ARLINGTON("Arlington Station, Boston, MA 02116, USA", "Arlington", 42.3519, -71.07089),
  COPLEY("Copley Station, Boston, MA 02116, USA", "Copley", 42.34997000000001, -71.07745),
  
  /*
  HYNES_CONVENTION_CENTER(),
  KENMORE(),

  //B
  BLANDFORD_ST(),
  BU_EAST(),
  BU_CENTRAL(),
  BU_WEST(),
  ST_PAUL_ST(),
  PLEASANT_ST(),
  BABCOCK_ST(),
  PACKARDS_CORNER(),
  HARVARD_AVE(),
  GRIGGS_ST_LONG_AVE(),
  ALLSTON_ST(),
  WARREN_ST(),
  WASHINGTON_ST(),
  SUTHERLAND_ST(),
  CHISWICK_RD(),
  CHESTNUT_HILL_AVE(),
  SOUTH_ST(),
  BC(),
  
  //C
  ST_MARYS_ST(),
  HAWES_ST(),
  KENT_ST(),
  ST_PAUL_ST(),
  COOLIDGE_CORNER(),
  SUMMIT_AVE(),
  BRANDON_HALL(),
  FAIRBANKS(),
  WASHINGTON_SQUARE(),
  TAPPAN_ST(),
  DEAN_RD(),
  ENGLEWOOD_AVE(),
  CLEVELAND_CIRCLE(),
  
  //D
  FENWAY(),
  LONGWOOD(),
  BROOKLINE_VILLAGE(),
  BROOKLINE_VILLS(),
  BEACONSFIELD(),
  RESERVOIR(),
  CHESTNUT_HILL(),
  NEWTON_CENTRE(),
  NEWTON_HIGHLANDS(),
  ELIOT(),
  WABAN(),
  WOODLAND(),
  RIVERSIDE(),
*/
  
  //E Line
  PRUDENTIAL("Prudential Station, The Shops at the Prudential Center, Boston, MA 02199, USA", "Prudential", 42.34553, -71.08193),
  SYMPHONY("Symphony Station, Northeastern University, Boston, MA 02115, USA", "Symphony", 42.34267000000001, -71.08507),
  NORTHEASTERN_UNIVERSITY("Northeastern University Station, Northeastern University, Boston, MA 02115, USA", "Northeastern University", 42.3404, -71.08881000000001),
  MUESUM_OF_FINE_ARTS("Museum of Fine Arts Station, Boston, MA 02115, USA", "Muesum of Fine Arts", 342.33765, -71.09554),
  LONGWOOD_MEDICAL_AREA("Longwood Station, Brookline, MA 02446, USA","Longwood", 42.34106000000001, -71.11027),
  BRIGHAM_CIRCLE("Brigham Circle Station, Boston, MA 02120, USA", "Brigham Circle", 42.33430000000001, -71.10466000000001),
  FENWOOD_RD("Fenwood Rd. Station, Boston, MA 02115, USA", "Fenwood",  42.33371, -71.10573000000002),
  MISSION_PARK("Mission Park Station, Boston, MA 02115, USA", "Mission Park", 42.33320000000001, -71.10976000000001),
  RIVERWAY("Riverway Station, Boston, MA 02115, USA", "Riverway", 42.33168000000001, -71.11193),
  BACK_OF_THE_HILL("Back of the Hill Station, Boston, MA 02130, USA", "Back of the Hill", 42.33014, -71.11131),
  HEATH_ST("Heath St. Station, Boston, MA 02130, USA", "Heath St.", 42.32868000000001, -71.11056);

  private final String address;
  private final String name;
  private final double latitude;
  private final double longitude;
  
  Stations(final String address, final String name, final double latitude, final double longitude) {
    this.address = address;
    this.name = name;
    this.latitude = latitude;
    this.longitude = longitude;
  }
  
  public String getAddress() {
    return address;
  }

  public String getName() {
    return name;
  }

  public double getLatitude() {
    return latitude;
  }

  public double getLongitude() {
    return longitude;
  }
}
