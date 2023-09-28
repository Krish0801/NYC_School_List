import SwiftUI
import shared

struct ContentView: View {
    @ObservedObject private(set) var viewModel: ViewModel

    var body: some View {
            NavigationView {
                listView()
                .navigationBarTitle("NYC School List")
                .navigationBarItems(trailing:
                    Button("Reload") {
                    viewModel.loadLaunches(forceReload: true)
                })
            }
        }

        private func listView() -> AnyView {
            switch viewModel.launches {
            case .loading:
                return AnyView(Text("Loading...").multilineTextAlignment(.center))
            case .result(let launches):
                return AnyView(List(launches) { launch in
                    NYCSchoolList(launch: launch)
                })
            case .error(let description):
                return AnyView(Text(description).multilineTextAlignment(.center))
            }
        }
    }

extension ContentView {
    enum LoadableLaunches {
        case loading
        case result([NYCSchool])
        case error(String)
    }

   @MainActor
   class ViewModel: ObservableObject {
       @Published var launches = LoadableLaunches.loading
   }
}

extension NYCSchool: Identifiable { }
