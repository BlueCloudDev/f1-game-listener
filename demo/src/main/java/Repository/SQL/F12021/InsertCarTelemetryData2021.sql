INSERT INTO t_car_telemetry_data_2021 (
  m_packet_id,
  m_index,
  m_session_lookup_id,
  m_session_time,
  m_frame_identifier,
  m_speed,
  m_throttle,
  m_steer,
  m_brake,
  m_clutch,
  m_gear,
  m_engine_rpm,
  m_drs,
  m_rev_lights_percent,
  m_rev_lights_bit_value,
  m_brakes_temperature_RL,
  m_brakes_temperature_RR,
  m_brakes_temperature_FL,
  m_brakes_temperature_FR,
  m_tyres_surface_temperature_RL,
  m_tyres_surface_temperature_RR,
  m_tyres_surface_temperature_FL,
  m_tyres_surface_temperature_FR,
  m_tyres_inner_temperature_RL,
  m_tyres_inner_temperature_RR,
  m_tyres_inner_temperature_FL,
  m_tyres_inner_temperature_FR,
  m_engine_temperature,
  m_tyres_pressure_RL,
  m_tyres_pressure_RR,
  m_tyres_pressure_FL,
  m_tyres_pressure_FR,
  m_surface_type_RL,
  m_surface_type_RR,
  m_surface_tyep_FL,
  m_surface_type_FR
) VALUES (
  ?,?,?,?,?,?,?,?,?,?,
  ?,?,?,?,?,?,?,?,?,?,
  ?,?,?,?,?,?,?,?,?,?,
  ?,?,?,?,?,?
)