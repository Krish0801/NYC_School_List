import SwiftUI
import shared

struct NYCSchoolList: View {
var launch: NYCSchool
    var body: some View {
            HStack() {
                VStack(alignment: .leading, spacing: 10.0) {
                    Text("DBN: \(launch.dbn)")
                    Text("School Name: \(launch.school_name)")
                }
                Spacer()
            }
        }
    }
