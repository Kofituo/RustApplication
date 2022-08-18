mod java_glue;

pub use crate::java_glue::*;

use android_logger::Config;
use log::Level;
use rifgen::rifgen_attr::*;

pub struct RustLog;

impl RustLog {
    //set up logging
    #[generate_interface]
    pub fn initialise_logging() {
        #[cfg(target_os = "android")]
            android_logger::init_once(
            Config::default()
                .with_min_level(Level::Trace)
                .with_tag("Rust"),
        );
        log_panics::init();
        log::info!("Logging initialised from Rust");
    }
}

pub struct Inputs {
    first: i64,
    second: i64,
}

impl Inputs {
    #[generate_interface(constructor)]
    pub fn new(first: i64, second: i64) -> Inputs {
        Self {
            first,
            second,
        }
    }
    #[generate_interface]
    pub fn addition(&self) -> i64 {
        self.first + self.second
    }
    #[generate_interface]
    pub fn subtraction(&self) -> i64 {
        self.first - self.second
    }
    #[generate_interface]
    pub fn multiplication(&self) -> i64 {
        self.first * self.second
    }
}