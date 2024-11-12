@import dto.UsersPage
@import dto.StartPage
@param UsersPage page


@template.layout.page(
content = @`
    <main class="flax-grow-1">
        <body >
        <section>
            <div class="container-fluid bg-light p-5">
                <div class="row">
                    <div class="col-md-10 col-lg-8 mx-auto text-dark">
                        <h1 class="display-3 mb-0">QuestGame</h1>
                        <p class="lead">${StartPage.getTitle()}</p>
                        <form action="/" method="post" class="rss-form text-body">
                            <div class="row">
                                <div class="col">
                                    <div class="form-floating">
                                        <input id="name-input" autofocus="" type="text" required="" name="name" aria-label="name"
                                               class="form-control w-80" placeholder="введи имя" autocomplete="off">
                                        <label for="name-input">Введи имя</label>
                                    </div>
                                </div>
                                <div class="col">
                                    <button type="submit" class="h-100 btn btn-lg btn-primary px-sm-3">Выбрать игру</button>
                                </div>
                            </div>
                        </form>
                        <div>
                            <table class="table table-bordered table-hover mt-3">
                                <thead>
                                <tr>
                                    <th scope="col">#</th>
                                    <th scope="col">Имя</th>
                                </tr>
                                </thead>
                                @for(var user : page.getUsers())
                                    <tbody>
                                    <tr>
                                        <th scope="row">${page.getUsers().indexOf(user) + 1}</th>
                                        <td><a href="users/${user.getId()}">${user.getName()}</a></td>
                                    </tr>
                                    </tbody>
                                @endfor
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </section>
        </body>
    </main>
`
)